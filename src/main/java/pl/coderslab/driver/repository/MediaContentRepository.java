package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.entity.MediaContent;

@Repository
public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {

}
