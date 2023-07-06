package pl.coderslab.driver.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

  private final TokenService tokenService;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  @Operation(summary = "Authenticate to get access JWT token")
  @PostMapping(path = "/token")
  public ResponseEntity<String> token(@RequestBody AuthenticationRequest request) {

    if (!userService.existsByUserName(request.getUserName())) {
      return ResponseEntity.status(401).body("User with username " + request.getUserName() + " not found!");
    }

    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getUserName(),
                      request.getPassword()
              )
      );
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body("Password is incorrect!");
    }
    String token = tokenService.generateToken(authentication);
    return ResponseEntity.status(200).body(token);
  }

  @Operation(summary = "Register new user")
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
