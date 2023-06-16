package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.entity.MediaContent;
import pl.coderslab.driver.repository.MediaContentRepository;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaContentService {

  private final MediaContentRepository mediaContentRepository;

  public Stream<MediaContent> findAll(){
    return mediaContentRepository.findAll().stream();
  }

  public MediaContent findById(Long id){
    return mediaContentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(MultipartFile mediaContent) throws IOException {
    String fileName = StringUtils.cleanPath(mediaContent.getOriginalFilename());
    MediaContent mediaContentDB = new MediaContent(fileName, mediaContent.getContentType(), mediaContent.getBytes());
    mediaContentRepository.save(mediaContentDB);
  }

  public void deleteById(Long id){
    mediaContentRepository.deleteById(id);
  }


}
