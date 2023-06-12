package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.entity.security.User;

import java.util.Optional;

@Repository
public interface UserParamsRepository extends JpaRepository<UserParams, Long> {

  @EntityGraph(attributePaths = {"passedAdvices", "user"})
  Optional<UserParams> findByUser(User user);
}
