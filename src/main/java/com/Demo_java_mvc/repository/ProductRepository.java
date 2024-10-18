package com.Demo_java_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Demo_java_mvc.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}