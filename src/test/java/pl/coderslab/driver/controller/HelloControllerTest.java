package pl.coderslab.driver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.coderslab.driver.config.WebSecurityConfiguration;
import pl.coderslab.driver.controller.security.AuthController;
import pl.coderslab.driver.service.security.TokenService;
import pl.coderslab.driver.service.security.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({HelloController.class, AuthController.class})
@Import({WebSecurityConfiguration.class, TokenService.class})
class HelloControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  private UserService userService;

  @Test
  void rootWhenUnauthenticatedThen401() throws Exception {
    this.mvc.perform(get("/"))
            .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "user", password = "password", roles = "USER")
  void tokenWithBasicThenGetToken() throws Exception {
    MvcResult result = this.mvc.perform(post("/api/token")).andExpect(status().isOk()).andReturn();

    assertThat(result.getResponse().getContentAsString()).isNotEmpty();
  }
}