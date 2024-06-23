package com.mangazo.ecommerce;

import com.mangazo.ecommerce.entity.Product;
import com.mangazo.ecommerce.entity.ProductCategory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	@Scope(value = "prototype")
	Product getProduct() {
		return new Product();
	}


	@Bean
	@Scope(value = "prototype")
	ProductCategory getProductCategory() {
		return new ProductCategory();
	}

}
