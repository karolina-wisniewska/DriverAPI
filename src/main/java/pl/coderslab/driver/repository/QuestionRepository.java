package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Override
  @EntityGraph(attributePaths = {"answers", "correctAnswer"})
  List<Question> findAll();

  @Override
  @EntityGraph(attributePaths = {"answers", "correctAnswer"})
  Optional<Question> findById(Long id);

}
