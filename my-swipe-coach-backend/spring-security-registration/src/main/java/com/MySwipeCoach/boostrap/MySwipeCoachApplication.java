package com.MySwipeCoach.boostrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.MySwipeCoach.*"})
@EntityScan("com.MySwipeCoach.*")
@EnableJpaRepositories ("com.MySwipeCoach.*") 
public class MySwipeCoachApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySwipeCoachApplication.class, args);
	}

}
