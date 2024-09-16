package com.Demo_java_mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.repository.UserRepository;

import jakarta.persistence.Id;

@Service

public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleSaveUser(User user) {
        User value = this.userRepository.save(user);
        System.out.println(value);
        return value;
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);

    }

    public void DeleteUser(long id) {
        this.userRepository.deleteById(id);

    }

}
