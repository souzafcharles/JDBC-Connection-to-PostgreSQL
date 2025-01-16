package com.souza.charles.model.dao;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import com.souza.charles.model.dao.impl.OrderDAOJDBC;
import com.souza.charles.model.dao.impl.ProductDAOJDBC;
import com.souza.charles.model.db.DB;

public class DAOFactory {

    public static OrderDAO createOrderDao() {
        return new OrderDAOJDBC(DB.getConnection());
    }

    public static ProductDAO createProductDao() {
        return new ProductDAOJDBC(DB.getConnection());
    }
}