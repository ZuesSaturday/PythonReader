package DAROARA.Saturday.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "DAROARA.Saturday")
public class SaturdayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaturdayApplication.class, args);
	}

}
