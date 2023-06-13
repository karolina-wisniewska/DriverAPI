package pl.coderslab.driver;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pl.coderslab.driver.config.RsaKeyProperties;
import pl.coderslab.driver.util.IndexCalculator;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@SecurityScheme(name = "driver-api", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class DriverApplication {

	@Bean
	public IndexCalculator calculator() {
		return new IndexCalculator();
	}

	public static void main(String[] args) {
		SpringApplication.run(DriverApplication.class, args);
	}

}
