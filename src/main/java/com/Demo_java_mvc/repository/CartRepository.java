package com.Demo_java_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Demo_java_mvc.domain.Cart;
import com.Demo_java_mvc.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User User);

}
