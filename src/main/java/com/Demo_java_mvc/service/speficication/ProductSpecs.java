package com.Demo_java_mvc.service.speficication;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.domain.Product_;

public class ProductSpecs {

    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");

    }

    // tìm min price
    public static Specification<Product> minPrice(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), price);

    }

    // tìm max price
    public static Specification<Product> maxPrice(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), price);

    }

    // tim factory
    public static Specification<Product> matchFactory(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.FACTORY), factory);

    }

    // tim nhieu factory
    public static Specification<Product> matchListFactory(List<String> factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);

    }

    // tim khoang price
    public static Specification<Product> matchPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and

        (criteriaBuilder.gt(root.get(Product_.price), min),
                (criteriaBuilder.le(root.get(Product_.price), max)));

    }
}
