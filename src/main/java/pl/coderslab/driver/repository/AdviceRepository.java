package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.entity.security.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

  @Override
  List<Advice> findAll();

  @Query("select a from UserParams u join u.passedAdvices a where u.user=:user")
  List<Advice> findAllByUser(@Param("user") User user);

  @Override
  Optional<Advice> findById(Long id);

  @Query("select a from Advice a where :tag member of a.tags")
  List<Advice> findByTag(@Param("tag") Tag tag);

}
