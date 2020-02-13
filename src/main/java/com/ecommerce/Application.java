package com.ecommerce;

import com.ecommerce.validators.OrderValidator;
import com.ecommerce.validators.ProductValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Validator productValidator() {
        return new ProductValidator();
    }


    @Bean
    public Validator orderValidator() {
        return new OrderValidator();
    }
}
