package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

}
