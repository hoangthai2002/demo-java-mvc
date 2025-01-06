package com.Demo_java_mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Demo_java_mvc.domain.Role;
import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.domain.dto.RegisterDTO;
import com.Demo_java_mvc.repository.OrderRepository;
import com.Demo_java_mvc.repository.ProductRepository;
import com.Demo_java_mvc.repository.RoleRepository;
import com.Demo_java_mvc.repository.UserRepository;

@Service

public class UserService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public User handleSaveUser(User user) {
        User value = this.userRepository.save(user);
        System.out.println(value);
        return value;
    }

    public Page<User> getAllUser(Pageable pageable) {
        return this.userRepository.findAll(pageable);
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

    public Role getRoleByName(String Name) {
        return this.roleRepository.findByName(Name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User(); //
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName()); // mapper
        user.setEmail(registerDTO.getEmail()); //
        user.setPassword(registerDTO.getPassword()); //
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);

    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();

    }

    public long countOrders() {
        return this.orderRepository.count();
    }
}
