package pl.coderslab.driver.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.pojo.AuthenticationRequest;
import pl.coderslab.driver.service.security.TokenService;
import pl.coderslab.driver.service.security.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
  private final TokenService tokenService;
  private final UserService userService;

  @Operation(summary = "Authenticate to get access JWT token")
  @PostMapping("/token")
  public String token(@RequestBody AuthenticationRequest request) {
    LOG.debug("Token requested for user: '{}", request.getUserName());
    String token = tokenService.generateToken(request);
    LOG.debug("Token granted {}", token);
    return token;
  }

  @Operation(summary = "User registration")
  @PostMapping(value = "/registration")
  public String createNewUser(@RequestBody AuthenticationRequest request) {
    boolean userExists = userService.existsByUserName(request.getUserName());
    if (userExists) {
      return "User with username " + request.getUserName() + " already exists!";
    } else {
      User user = new User();
      user.setUserName(request.getUserName());
      user.setPassword(request.getPassword());
      userService.saveUser(user);
    }
    return "User " + request.getUserName() + " registered successfully!";
  }

}
