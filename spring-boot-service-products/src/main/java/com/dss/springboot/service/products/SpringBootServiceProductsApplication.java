package com.dss.springboot.service.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringBootServiceProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootServiceProductsApplication.class, args);
    }

}
