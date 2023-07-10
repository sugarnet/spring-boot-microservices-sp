package com.dss.springboot.service.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.dss.springboot.service.commons.model.entity"})
public class SpringBootServiceProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootServiceProductsApplication.class, args);
    }

}
