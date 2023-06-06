package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.UserParams;

@Repository
public interface UserParamsRepository extends JpaRepository<UserParams, Long> {

}
