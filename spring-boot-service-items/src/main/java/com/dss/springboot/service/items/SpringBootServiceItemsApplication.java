package com.dss.springboot.service.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RibbonClient(name = "service-products")
@EnableFeignClients
@SpringBootApplication
public class SpringBootServiceItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServiceItemsApplication.class, args);
	}

}
