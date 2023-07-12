package pl.coderslab.driver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
				webEnvironment = SpringBootTest.WebEnvironment.MOCK,
				classes = DriverApplicationTests.class)
@AutoConfigureMockMvc
class DriverApplicationTests {

	@Test
	void contextLoads() {
	}

}
