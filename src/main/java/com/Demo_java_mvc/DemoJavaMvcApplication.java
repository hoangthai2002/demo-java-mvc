package com.Demo_java_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
//exclude loại bỏ
//include thêm vào
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class DemoJavaMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJavaMvcApplication.class, args);
	}

}
