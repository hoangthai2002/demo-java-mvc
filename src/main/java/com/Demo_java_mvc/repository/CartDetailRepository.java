package com.Demo_java_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Demo_java_mvc.domain.Cart;
import com.Demo_java_mvc.domain.CartDetail;
import com.Demo_java_mvc.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    // boolean existByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);
}
