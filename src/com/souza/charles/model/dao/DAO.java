package com.souza.charles.model.dao;
/*
  Video Tutorial: Object-oriented and SQL revision using Java and JDBC
  Instructor: Prof. Dr. Nelio Alves - DevSuperior
  Example adapted by: Charles Fernandes de Souza
  Date: January 16, 2025
 */

import java.util.List;

public interface DAO<T> {
    void insert(T obj);
    void update(T obj);
    void deleteById(Integer id);
    T findById(Integer id);
    List<T> findAll();
}