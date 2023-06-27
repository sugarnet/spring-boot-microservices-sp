package com.dss.springboot.service.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SpringBootServiceConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServiceConfigServerApplication.class, args);
	}

}
