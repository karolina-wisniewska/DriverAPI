package pl.coderslab.driver.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.service.security.UserService;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class UserConverter {
  private final UserService userService;

  public User getUserFromPrincipal(Principal principal){
    String username = principal.getName();
    return userService.findUserByUserName(username);
  }
}
