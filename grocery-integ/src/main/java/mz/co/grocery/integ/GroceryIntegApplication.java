package mz.co.grocery.integ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = { "mz.co.grocery.core", "mz.co.grocery.integ", "mz.co.grocery.persistence" })
@PropertySource("classpath:application.properties")
public class GroceryIntegApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GroceryIntegApplication.class, args);
	}
}
