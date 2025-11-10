package ar.edu.ubp.das.ristorino_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RistorinoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RistorinoBackendApplication.class, args);
	}

}
