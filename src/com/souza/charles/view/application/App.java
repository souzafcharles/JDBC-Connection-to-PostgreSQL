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
import com.souza.charles.model.entities.enums.OrderStatus;

import java.sql.SQLException;
import java.time.Instant;
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

        System.out.println("\n********** TEST 02: Order findAll **********");
        List<Order>listOrder = orderDAO.findAll();
        for (Order o : listOrder) {
            System.out.println(o);
        }

        System.out.println("\n********** TEST 02: Product findAll **********");
        List<Product> listProduct = productDAO.findAll();
        for (Product p : listProduct) {
            System.out.println(p);
        }

        System.out.println("\n********** TEST 03: Order insert **********");
        Order newOrder = new Order(null, -23.555555, -46.666666, Instant.now(), OrderStatus.PENDING);
        orderDAO.insert(newOrder);
        System.out.println("Inserted! New Order id = " + newOrder.getId());

        System.out.println("\n********** TEST 03: Product insert **********");
        Product newProduct = new Product(null, "Pizza Margherita Deluxe", 35.0, "Uma pizza clássica Margherita com tomates frescos, manjericão, muçarela de búfala e um toque de azeite extra virgem.", "https://github.com/souzafcharles/4.png");
        productDAO.insert(newProduct);
        System.out.println("Inserted! New Product Name = " + newProduct.getName());

        System.out.println("\n********** TEST 04: Order update **********");
        order = orderDAO.findById(5);
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderDAO.update(order);
        System.out.println("Update completed!");

        System.out.println("\n********** TEST 05: Product update **********");
        product = productDAO.findById(4);
        product.setPrice(45.0);
        productDAO.update(product);
        System.out.println("Update completed!");
    }
}