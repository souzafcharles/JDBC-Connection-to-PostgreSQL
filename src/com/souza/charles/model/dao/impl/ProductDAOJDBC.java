package com.souza.charles.model.dao.impl;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import com.souza.charles.model.dao.ProductDAO;
import com.souza.charles.model.db.DB;
import com.souza.charles.model.db.DbException;
import com.souza.charles.model.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOJDBC implements ProductDAO {

    private Connection connection;

    public ProductDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product product) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO tb_product (name, price, image_uri, description) "
                            + "VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUri());
            preparedStatement.setString(4, product.getDescription());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    product.setId(id);
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
    public void update(Product product) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Product findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tb_product "
                            + "WHERE Id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return instantiateProduct(resultSet);
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
    public List<Product> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tb_product "
                            + "ORDER BY name");
            resultSet = preparedStatement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = instantiateProduct(resultSet);
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
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