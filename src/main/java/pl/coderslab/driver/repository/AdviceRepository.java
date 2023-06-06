package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Advice;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

}
