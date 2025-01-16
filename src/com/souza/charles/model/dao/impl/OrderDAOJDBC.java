package com.souza.charles.model.dao.impl;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */
import com.souza.charles.model.dao.OrderDAO;
import com.souza.charles.model.entities.Order;

import java.sql.Connection;
import java.util.List;

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
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}