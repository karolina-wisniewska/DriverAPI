package pl.coderslab.driver.service;

import jakarta.persistence.EntityExistsException;
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

  public Tag save(Tag tag){
    Optional<Tag> savedTag = findByName(tag.getName());
    if(savedTag.isPresent()){
      throw new EntityExistsException("Tag named" + tag.getName() + "already exists");
    }
    return tagRepository.save(tag);
  }

  public Tag update(Tag updatedTag){
    return tagRepository.save(updatedTag);
  }

  public void deleteById(Long id){
    tagRepository.deleteById(id);
  }
}
