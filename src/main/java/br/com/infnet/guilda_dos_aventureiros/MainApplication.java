package br.com.infnet.guilda_dos_aventureiros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MainApplication {
	void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
