package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Override
  @EntityGraph(attributePaths = {"answers", "correctAnswer"})
  List<Question> findAll();

  @EntityGraph(attributePaths = {"answers", "correctAnswer"})
  @Query("select q from Training t join t.questions q where t.id=:trainingId")
  List<Question> findAllByTraining(@Param("trainingId") Long trainingId);

  @Override
  @EntityGraph(attributePaths = {"answers", "correctAnswer"})
  Optional<Question> findById(Long id);


}
