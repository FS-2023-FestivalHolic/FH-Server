package com.gdsc.festivalholic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class FestivalHolicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FestivalHolicApplication.class, args);
	}

}
