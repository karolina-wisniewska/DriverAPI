package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Training;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

  @Override
  @EntityGraph(attributePaths = {"advice", "questions"})
  List<Training> findAll();

  @Override
  @EntityGraph(attributePaths = {"advice", "questions"})
  Optional<Training> findById(Long id);

}
