package com.Demo_java_mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Demo_java_mvc.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User valueUser);

    void deleteById(long id);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findById(long id);

    Boolean existsByEmail(String email);

    User findByEmail(String email);

}
