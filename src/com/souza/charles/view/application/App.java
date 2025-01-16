package com.souza.charles.view.application;
 /*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import com.souza.charles.model.db.DB;
import com.souza.charles.model.entities.Order;
import com.souza.charles.model.entities.Product;
import com.souza.charles.model.entities.enums.OrderStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) throws SQLException {

        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        //ResultSet resultSet = statement.executeQuery("select * from tb_product");
        ResultSet resultSet = statement.executeQuery(
                            "SELECT * FROM tb_order "
                                + "INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
                                + "INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");

        Map<Long, Order> orderMap = new HashMap<>();
        Map<Long, Product> productMap = new HashMap<>();

        while (resultSet.next()) {
            Long orderId = resultSet.getLong("order_id");
            if (orderMap.get(orderId) == null){
                Order order = instantiateOrder(resultSet);
                orderMap.put(orderId, order);
            }
            Long productId = resultSet.getLong("product_id");
            if(productMap.get(productId) == null){
                Product product = instantiateProduct(resultSet);
                productMap.put(productId, product);
            }
            orderMap.get(orderId).getProducts().add(productMap.get(productId));
        }

        for (Long orderId : orderMap.keySet()) {
            System.out.println(orderMap.get(orderId));
            for (Product product : orderMap.get(orderId).getProducts()) {
                System.out.println(product);
            }
            System.out.println("***");
        }
    }

    public static Product instantiateProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("product_id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDescription(resultSet.getString("description"));
        product.setImageUri(resultSet.getString("image_uri"));
        return product;
    }

    public static Order instantiateOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("order_id"));
        order.setLatitude(resultSet.getDouble("latitude"));
        order.setLongitude(resultSet.getDouble("longitude"));
        order.setMoment(resultSet.getTimestamp("moment").toInstant());
        order.setOrderStatus(OrderStatus.values()[resultSet.getInt("status")]);
        return order;
    }
}
