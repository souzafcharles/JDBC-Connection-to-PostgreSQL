package com.souza.charles.view.application;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import com.souza.charles.model.dao.DAOFactory;
import com.souza.charles.model.dao.OrderDAO;
import com.souza.charles.model.dao.ProductDAO;
import com.souza.charles.model.entities.Order;
import com.souza.charles.model.entities.Product;

import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {

        OrderDAO orderDAO = DAOFactory.createOrderDAO();
        ProductDAO productDAO = DAOFactory.createProductDAO();

        System.out.println("\n********** TEST 01: Order findById **********");
        Order order = orderDAO.findById(1);
        System.out.println(order);

        System.out.println("\n********** TEST 01: Product findById **********");
        Product product = productDAO.findById(2);
        System.out.println(product);

        System.out.println("\n********** TEST 03: Order findAll **********");
        List<Order>listOrder = orderDAO.findAll();
        for (Order o : listOrder) {
            System.out.println(o);
        }

        System.out.println("\n********** TEST 03: Product findAll **********");
        List<Product> listProduct = productDAO.findAll();
        for (Product p : listProduct) {
            System.out.println(p);
        }
    }
}