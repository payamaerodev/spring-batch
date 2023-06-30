package com.example.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableBatchProcessing
@EnableAsync
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);

	}
}
