package pl.coderslab.driver.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.security.Role;
import pl.coderslab.driver.repository.security.RoleRepository;

@RequiredArgsConstructor
@Service
public class RoleService{
  private final RoleRepository roleRepository;

  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }
}
