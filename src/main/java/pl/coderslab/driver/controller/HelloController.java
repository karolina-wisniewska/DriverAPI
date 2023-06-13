package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "driver-api")
public class HelloController {

  @GetMapping("/hello")
  public String hello(Principal principal) {
    return "Hello, " + principal.getName();
  }

}
