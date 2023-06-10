package pl.coderslab.driver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public List<Tag> findAll(){
    return tagRepository.findAll();
  }

  public Optional<Tag> findById(Long id){
    return tagRepository.findById(id);
  }

  public Optional<Tag> findByName(String name){
    return tagRepository.findByName(name);
  }

  public void save(Tag tag){
    tagRepository.save(tag);
  }

  public void deleteById(Long id){
    tagRepository.deleteById(id);
  }


}
