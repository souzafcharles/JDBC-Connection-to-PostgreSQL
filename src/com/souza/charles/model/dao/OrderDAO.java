package com.souza.charles.model.dao;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import com.souza.charles.model.entities.Order;

public interface OrderDAO extends DAO<Order> {
    void insertOrderProductRelation(Long orderId, Long productId);
}
