package com.dss.springboot.service.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringBootServiceEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServiceEurekaServerApplication.class, args);
	}

}
