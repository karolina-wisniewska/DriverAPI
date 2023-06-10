package pl.coderslab.driver.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.security.Role;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.repository.security.RoleRepository;
import pl.coderslab.driver.repository.security.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(true);
    Role userRole = roleRepository.findByName("ROLE_USER");
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    return userRepository.save(user);
  }

  public User findByRole(Role role){
    return userRepository.findByRole(role);
  };

}
