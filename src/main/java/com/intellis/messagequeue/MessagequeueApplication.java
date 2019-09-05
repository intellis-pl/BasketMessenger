package com.intellis.messagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class MessagequeueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagequeueApplication.class, args);
	}
}
