package pl.coderslab.driver.model.security;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CurrentUser extends User {

  private final pl.coderslab.driver.entity.security.User user;

  public CurrentUser(String username, String password,
                     Collection<? extends GrantedAuthority> authorities,
                     pl.coderslab.driver.entity.security.User user) {
    super(username, password, authorities);
    this.user = user;
  }



}
