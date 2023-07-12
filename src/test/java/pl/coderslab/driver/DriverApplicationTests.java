package pl.coderslab.driver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@SpringBootTest(
				webEnvironment = SpringBootTest.WebEnvironment.MOCK,
				classes = DriverApplication.class)
@AutoConfigureMockMvc
class DriverApplicationTests {

	@Test
	void contextLoads() {
	}

}
