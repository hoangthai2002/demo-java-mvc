package com.Demo_java_mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Demo_java_mvc.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User valueUser);

    List<User> findByEmail(String email);

}
