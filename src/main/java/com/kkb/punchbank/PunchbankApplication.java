package com.kkb.punchbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableJpaAuditing
@EnableRetry
public class PunchbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(PunchbankApplication.class, args);
	}

}
