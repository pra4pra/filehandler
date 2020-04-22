package com.prathap.file.filehandler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.prathap.file.filehandler.service.FilehandlerService;

@SpringBootApplication
public class FilehandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilehandlerApplication.class, args);
	}

	
	@Bean
	CommandLineRunner init(FilehandlerService filehandlerService) {
		return (args) -> {
			filehandlerService.init();
		};
	}
}
