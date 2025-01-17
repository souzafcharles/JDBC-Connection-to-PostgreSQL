package com.souza.charles.model.dao.impl;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import com.souza.charles.model.dao.OrderDAO;
import com.souza.charles.model.db.DB;
import com.souza.charles.model.db.DbException;
import com.souza.charles.model.entities.Order;
import com.souza.charles.model.entities.Product;
import com.souza.charles.model.entities.enums.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOJDBC implements OrderDAO {

    private Connection connection;

    public OrderDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Order order) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO tb_order (latitude, longitude, moment, status) "
                            + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDouble(1, order.getLatitude());
            preparedStatement.setDouble(2, order.getLongitude());
            preparedStatement.setTimestamp(3, Timestamp.from(order.getMoment()));
            preparedStatement.setInt(4, order.getOrderStatus().ordinal());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    order.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Order order) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE tb_order "
                            + "SET latitude = ?, longitude = ?, moment = ?, status = ? "
                            + "WHERE id = ?");

            preparedStatement.setDouble(1, order.getLatitude());
            preparedStatement.setDouble(2, order.getLongitude());
            preparedStatement.setTimestamp(3, Timestamp.from(order.getMoment()));
            preparedStatement.setInt(4, order.getOrderStatus().ordinal());
            preparedStatement.setLong(5, order.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Order findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT tb_order.*, "
                            + "tb_product.id AS ProductId, tb_product.name AS ProductName, tb_product.price AS ProductPrice, "
                            + "tb_product.description AS ProductDescription, tb_product.image_uri AS ProductImageUri "
                            + "FROM tb_order "
                            + "INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
                            + "INNER JOIN tb_product ON tb_order_product.product_id = tb_product.id "
                            + "WHERE tb_order.id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return instantiateOrder(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public List<Order> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tb_order "
                            + "ORDER BY id");
            resultSet = preparedStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = instantiateOrder(resultSet);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closePreparedStatement(preparedStatement);
        }
    }

    private Order instantiateOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setLatitude(resultSet.getDouble("latitude"));
        order.setLongitude(resultSet.getDouble("longitude"));
        order.setMoment(resultSet.getTimestamp("moment").toInstant());
        order.setOrderStatus(OrderStatus.values()[resultSet.getInt("status")]);
        return order;
    }

    private Product instantiateProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDescription(resultSet.getString("description"));
        product.setImageUri(resultSet.getString("image_uri"));
        return product;
    }
}