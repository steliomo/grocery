package mz.co.grocery.integ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("mz.co.grocery.core, mz.co.grocery.integ")
public class GroceryIntegApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GroceryIntegApplication.class, args);
	}
}
