package pl.coderslab.driver.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.driver.entity.security.Role;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.model.security.LocalUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userService.findUserByUserName(userName);
    if (user == null) {
      throw new UsernameNotFoundException(userName);
    }
    List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
    return new LocalUser(user.getUserName(),user.getPassword(),
            authorities, user);
  }


  private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
    Set<GrantedAuthority> roles = new HashSet<>();
    for (Role role : userRoles) {
      roles.add(new SimpleGrantedAuthority(role.getName()));
    }
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
    return grantedAuthorities;
  }

}
