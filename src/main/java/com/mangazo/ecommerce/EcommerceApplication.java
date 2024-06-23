package com.mangazo.ecommerce;

import com.mangazo.ecommerce.entity.Address;
import com.mangazo.ecommerce.entity.OrderItem;
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

	@Bean
	@Scope(value = "prototype")
	Address getAddress() {
		return new Address();
	}

	@Bean
	@Scope(value = "prototype")
	OrderItem getOrderItem() {
		return new OrderItem();
	}

}
