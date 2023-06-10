package pl.coderslab.driver.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.security.Role;
import pl.coderslab.driver.entity.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  @Query("select u from User u where :role member of u.roles")
  User findByRole(@Param("role") Role role);
}
