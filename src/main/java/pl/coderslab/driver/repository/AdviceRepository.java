package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Advice;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

  @Override
  @EntityGraph(attributePaths = {"mediaContent", "tags"})
  List<Advice> findAll();

  @Override
  @EntityGraph(attributePaths = {"mediaContent", "tags"})
  Optional<Advice> findById(Long id);

}
