![GitHub language count](https://img.shields.io/github/languages/count/souzafcharles/JDBC-Connection-to-PostgreSQL)
![GitHub top language](https://img.shields.io/github/languages/top/souzafcharles/JDBC-Connection-to-PostgreSQL)
![GitHub license](https://img.shields.io/github/license/souzafcharles/JDBC-Connection-to-PostgreSQL)
![GitHub last commit](https://img.shields.io/github/last-commit/souzafcharles/JDBC-Connection-to-PostgreSQL)

# :coffee: Object-oriented and SQL revision using Java and JDBC

:triangular_flag_on_post: Prof. Dr. Nelio Alves - [DevSuperior](https://devsuperior.com.br/)

:black_nib: IDE used: `IntelliJ IDEA`

:coffee: Java | version: `21`

:elephant: Database | `PostgresSQL`

***
## Instructions:
Develop a Java application that connects to a database using `JDBC` and interfaces following the `DAO pattern`, featuring entity classes for `Order` and `Product`. The target `PostgresSQL` database is `chdeliver`.

## Steps to Follow:

### 1. Database Configuration:
- Add the lib `postgresql-42.2.18.jar` to the project;
- Establish a database named `chdeliver` using `pgAdmin 4`;
- In the project's root folder, create a file named `db.properties` containing the connection data:

#### db.properties File:

```SQL
user=postgres
password=******
dburl=jdbc:postgresql://localhost:5432/chdeliver
useSSL=false
```
***
### 2. Database Table Creation:
- Create an SQL script to store data in the `chdeliver` database.
#### database.sql Script:
```SQL
-- Drop table 'tb_order_product' if it exists
DROP TABLE IF EXISTS tb_order_product;

-- Drop table 'tb_order' if it exists
DROP TABLE IF EXISTS tb_order;

-- Drop table 'tb_product' if it exists
DROP TABLE IF EXISTS tb_product;

-- Create table 'tb_order' if it does not exist
CREATE TABLE IF NOT EXISTS tb_order (
    id int8 generated by default as identity, 
    latitude float8, 
    longitude float8, 
    moment TIMESTAMP WITHOUT TIME ZONE, 
    status int4, 
    primary key (id)
);

-- Create table 'tb_order_product' if it does not exist
CREATE TABLE IF NOT EXISTS tb_order_product (
    order_id int8 not null, 
    product_id int8 not null, 
    primary key (order_id, product_id)
);

-- Create table 'tb_product' if it does not exist
CREATE TABLE IF NOT EXISTS tb_product (
    id int8 generated by default as identity, 
    description TEXT, 
    image_uri varchar(255), 
    name varchar(255), 
    price float8, 
    primary key (id)
);

-- Add foreign key constraints to 'tb_order_product' only if they do not exist
ALTER TABLE tb_order_product 
ADD CONSTRAINT fk_tb_order_product_tb_product 
FOREIGN KEY (product_id) REFERENCES tb_product;

ALTER TABLE tb_order_product 
ADD CONSTRAINT fk_tb_order_product_tb_order 
FOREIGN KEY (order_id) REFERENCES tb_order;

-- Insert data into 'tb_product'
INSERT INTO tb_product (name, price, image_uri, description) VALUES 
('Pizza de Calabresa Grande', 50.0, 'https://github.com/souzafcharles/1.png', 'Deliciosa pizza de calabresa com queijo muçarela, molho de tomate especial e orégano. Disponível em tamanho grande.'),
('Pizza Quatro Queijos Média', 40.0, 'https://github.com/souzafcharles/2.png', 'Uma explosão de sabores com queijo gorgonzola, queijo provolone, queijo parmesão e queijo muçarela. Ideal para compartilhar.'),
('Pizza de Escarola com Bacon Grande', 60.0, 'https://github.com/souzafcharles/3.png', 'Combinação perfeita de escarola refogada com bacon crocante, coberta com queijo muçarela e molho branco. Uma delícia!');

-- Insert data into 'tb_order'
INSERT INTO tb_order (status, latitude, longitude, moment) VALUES 
(0, 213123, 12323, TIMESTAMP WITH TIME ZONE '2025-01-14T11:00:00Z'),
(1, 3453453, 3534534, TIMESTAMP WITH TIME ZONE '2025-01-15T11:00:00Z');

-- Insert data into 'tb_order_product'
INSERT INTO tb_order_product (order_id, product_id) VALUES 
(1, 1),
(1, 2),
(2, 2),
(2, 3);
```
***
### 3. Entities Classes:
- Create the `Order` and `Product` classes;

![Order and Product Entities](https://github.com/souzafcharles/JDBC-Connection-to-PostgreSQL/blob/main/img/order-product-entities.png)

#### Entities classes checklist:
- Attributes;
- Constructors;
- Getters/Setters;
- hashCode and equals;
- toString;
- Implements Serializable.
***
### 4. Tutorial Video Tests Checklist:
- Primary key and Foreign key;
- DDL (create table, alter table);
- SQL:
  - INSERT;
  - SELECT;
  - INNER JOIN;
- Classes and Objects;
- Encapsulation, get/set;
- Enumerated types;
- Object Composition;
- Collections (List and Map);
- Accessing data in a Relational Database and instantiating corresponding objects;
#### Query to retrieve Orders with their Products:
```SQL
SELECT * FROM tb_order
INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id
INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id
```
***
▶️ [Tutorial Video DevSuperior](https://www.youtube.com/watch?v=xC_yKw3MYX4&ab_channel=DevSuperior)
***
### 5. DAO Pattern (Data Access Object):
- Create the `ProductDAO` and `OrderDAO` interfaces following the `DAO Pattern`.
#### Generic DAO Class:
```java
public interface DAO<T> {
    void insert(T obj);
    void update(T obj);
    void deleteById(Integer id);
    T findById(Integer id);
    List<T> findAll();
}
```
- Implement the classes `OrderDAOJDBC` and `OrderDAOJDBC`.
- Create the `DAOFactory` to instantiate DAO objects.

![DaoFactory](https://github.com/souzafcharles/JDBC-Connection-to-PostgreSQL/blob/main/img/daoFactory.png)
***
### 6. CRUD Operations Implementation:
***
### 6.1 `findById` - Implement the search by `ID`:
#### App Class:
```java
System.out.println("\n********** TEST 01: Order findById **********");
OrderDAO orderDAO = DAOFactory.createOrderDAO();
Order order = orderDAO.findById(1);
System.out.println(order);

System.out.println("\n********** TEST 01: Product findById **********");
ProductDAO productDAO = DAOFactory.createProductDAO();     
Product product = productDAO.findById(2);
System.out.println(product);
```
#### Order SQL Query:
```SQL
SELECT tb_order.*, 
       tb_product.id AS ProductId, 
       tb_product.name AS ProductName, 
       tb_product.price AS ProductPrice, 
       tb_product.description AS ProductDescription, 
       tb_product.image_uri AS ProductImageUri 
FROM tb_order 
INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id 
INNER JOIN tb_product ON tb_order_product.product_id = tb_product.id 
WHERE tb_order.id = 1;
```
#### Product SQL Query:
```SQL
SELECT * FROM tb_product 
WHERE id = 2;
```
***
### 6.2 `findAll` - Implement the search for `all` records:
#### App Class:
```java
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
```
#### Order SQL Query:
```SQL
SELECT * FROM tb_order 
ORDER BY id;
```
#### Product SQL Query:
```SQL
SELECT * FROM tb_product 
ORDER BY name;
```
***
### 6.3 `insert` - Implement the `insertion` of new records:
#### App Class:
```java
System.out.println("\n********** TEST 03: Order insert **********");
Order newOrder = new Order(null, -23.555555, -46.666666, Instant.now(), OrderStatus.PENDING);
orderDAO.insert(newOrder);
System.out.println("Inserted! New Order id = " + newOrder.getId());

System.out.println("\n********** TEST 03: Product insert **********");
Product newProduct = new Product(null, "Pizza Margherita Deluxe", 35.0, "Uma pizza clássica Margherita com tomates frescos, manjericão, muçarela de búfala e um toque de azeite extra virgem.", "https://github.com/souzafcharles/4.png");
productDAO.insert(newProduct);
System.out.println("Inserted! New Department Name = " + newProduct.getName());
```
#### Order SQL Query:
```SQL
INSERT INTO tb_order (latitude, longitude, moment, status)
VALUES (?, ?, ?, ?)
```
#### Product SQL Query:
```SQL
INSERT INTO tb_product (name, price, image_uri, description)
VALUES (?,?,?,?)
```  
### 6.4 `relation insert` - Implement the `Order-Product relation insert` of new records:

Add the method signature `insertOrderProductRelation` to the `OrderDAO` interface.

OrderDAO Interface Class:
```java
public interface OrderDAO extends DAO<Order> {
    void insertOrderProductRelation(Long orderId, Long productId);
}
```
#### Order SQL Query:
```SQL
INSERT INTO tb_order_product (order_id, product_id)
 VALUES (?, ?)
```
***
### 6.5 `update` - Implement the `update` of existing records:
### App Class:
```java
System.out.println("\n********** TEST 05: Order update **********");
order = orderDAO.findById(5);
order.setOrderStatus(OrderStatus.DELIVERED);
orderDAO.update(order);
System.out.println("Update completed!");

System.out.println("\n********** TEST 05: Product update **********");
product = productDAO.findById(4);
product.setPrice(45.0);
productDAO.update(product);
System.out.println("Update completed!");
```
#### Order SQL Query:
```SQL
UPDATE tb_order 
SET latitude = ?, longitude = ?, moment = ?, status = ? 
WHERE id = ?
```
#### Product SQL Query:
```SQL
UPDATE tb_product "
SET name = ?, price = ?, image_uri = ?, description = ?
WHERE id = ?
``` 
***
### 6.6 `delete` - Implement the `deletion` of records:
#### App Class:
```java
System.out.println("\n********** TEST 05: Order delete **********");
System.out.print("Enter the Order Id for deleteById test: ");
int id = scanner.nextInt();
orderDAO.deleteById(id);
System.out.println("Delete completed!");

System.out.println("\n********** TEST 05: Product delete **********");
System.out.print("Enter the Product Id for deleteById test: ");
id = scanner.nextInt();
productDAO.deleteById(id);
System.out.println("Delete completed!");
``` 
#### Order SQL Query (tb_order_product):
Delete references in the relationship table.
```SQL
DELETE FROM tb_order_product 
WHERE id = ?;
```
#### Product SQL Query:
Delete references in the relationship table.
```SQL
DELETE FROM tb_order_product
WHERE id = ?;
```
#### Order SQL Query:
Delete the Order.
```SQL
DELETE FROM tb_order 
WHERE id = ?;
```
#### Product SQL Query:
Delete the Product.
```SQL
DELETE FROM tb_product 
WHERE id = ?;
```