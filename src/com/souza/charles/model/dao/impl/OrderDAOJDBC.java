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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAOJDBC implements OrderDAO {

    private Connection connection;

    public OrderDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Order order) {

    }

    @Override
    public void update(Order order) {

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