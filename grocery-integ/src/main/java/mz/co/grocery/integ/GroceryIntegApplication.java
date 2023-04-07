package mz.co.grocery.integ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "mz.co.grocery.core", "mz.co.grocery.integ", "mz.co.grocery.persistence" })
public class GroceryIntegApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GroceryIntegApplication.class, args);
	}

}
