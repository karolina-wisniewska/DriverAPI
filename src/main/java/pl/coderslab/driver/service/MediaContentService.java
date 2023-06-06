package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.MediaContent;
import pl.coderslab.driver.repository.MediaContentRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaContentService {

  private final MediaContentRepository mediaContentRepository;

  public List<MediaContent> findAll(){
    return mediaContentRepository.findAll();
  }

  public MediaContent findById(Long id){
    return mediaContentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(MediaContent mediaContent){
    mediaContentRepository.save(mediaContent);
  }

  public void deleteById(Long id){
    mediaContentRepository.deleteById(id);
  }


}
