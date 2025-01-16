package com.souza.charles.model.dao.impl;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */
import com.souza.charles.model.dao.ProductDAO;
import com.souza.charles.model.entities.Product;

import java.sql.Connection;
import java.util.List;

public class ProductDAOJDBC implements ProductDAO {

    private Connection connection;

    public ProductDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Product findById(Integer id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }
}