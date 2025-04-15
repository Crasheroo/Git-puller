package com.group.Github_Puller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GithubPullerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubPullerApplication.class, args);
	}

}
