package com.Demo_java_mvc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    // Taoj mới sản phẩm
    public Product createProduct(Product pr) {
        return this.productRepository.save(pr);

    }

    // lấy tất cả sản phẩm lên
    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    // lấy id
    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    // xóa theo id
    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

}
