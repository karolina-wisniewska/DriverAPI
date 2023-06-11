package pl.coderslab.driver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.coderslab.driver.config.WebSecurityConfiguration;
import pl.coderslab.driver.controller.security.AuthController;
import pl.coderslab.driver.service.security.TokenService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({HelloController.class, AuthController.class})
@Import({WebSecurityConfiguration.class, TokenService.class})
class HelloControllerTest {

  @Autowired
  MockMvc mvc;

  @Test
  void rootWhenUnauthenticatedThen401() throws Exception {
    this.mvc.perform(get("/"))
            .andExpect(status().isUnauthorized());
  }

  @Test
  void tokenWithBasicThenGetToken() throws Exception {
    MvcResult result = this.mvc.perform(post("/token").with(httpBasic("Admin", "Admin"))).andExpect(status().isOk()).andReturn();

    assertThat(result.getResponse().getContentAsString()).isNotEmpty();
  }
}