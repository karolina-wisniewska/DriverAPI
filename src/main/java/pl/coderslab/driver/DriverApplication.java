package pl.coderslab.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pl.coderslab.driver.config.RsaKeyProperties;
import pl.coderslab.driver.util.IndexCalculator;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class DriverApplication {

	@Bean
	public IndexCalculator calculator() {
		return new IndexCalculator();
	}

	public static void main(String[] args) {
		SpringApplication.run(DriverApplication.class, args);
	}

}
