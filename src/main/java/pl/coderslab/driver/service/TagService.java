package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.repository.TagRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public List<Tag> findAll(){
    return tagRepository.findAll();
  }

  public Tag findById(Long id){
    return tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(Tag tag){
    tagRepository.save(tag);
  }

  public void deleteById(Long id){
    tagRepository.deleteById(id);
  }


}
