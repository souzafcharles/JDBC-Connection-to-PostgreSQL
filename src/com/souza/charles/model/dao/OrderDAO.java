package com.souza.charles.model.dao;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 17, 2025
 */

import com.souza.charles.model.entities.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order> {
    List<Order> findOrdersAssociatedProducts();
    void insertOrderProductRelation(Long orderId, Long productId);
}
