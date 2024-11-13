package com.cs7319.finalproject.note_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
	org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
	org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
public class NoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteServiceApplication.class, args);
	}

}
