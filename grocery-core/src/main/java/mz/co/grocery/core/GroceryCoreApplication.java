package mz.co.grocery.core;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * @author Stélio Moiane
 *
 */
@SpringBootApplication
public class GroceryCoreApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GroceryCoreApplication.class, args);
	}

	@Profile(value = "test")
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}
}
