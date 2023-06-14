package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.entity.MediaContent;
import pl.coderslab.driver.model.MediaContentDto;
import pl.coderslab.driver.service.MediaContentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/media")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class MediaContentController {

  private final MediaContentService mediaContentService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<MediaContentDto> getAllMediaContents() {
    return mediaContentService.findAll()
            .stream()
            .map(this::convertMediaContentEntityToDto)
            .collect(Collectors.toList());
  }

  @GetMapping(value = "/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  public MediaContentDto getMediaContentById(@PathVariable long mediaId) {
    return Optional.ofNullable(mediaContentService.findById(mediaId))
            .map(this::convertMediaContentEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createMediaContent(@RequestBody MediaContentDto mediaContent) {
    mediaContentService.save(convertMediaContentDtoToEntity(mediaContent));
  }

  @PutMapping("/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateMediaContent(@PathVariable long mediaId, @RequestBody MediaContent mediaContent) {
    MediaContent mediaContentFromDb = mediaContentService.findById(mediaId);
    mediaContentFromDb.setName(mediaContent.getName());
    mediaContentFromDb.setType(mediaContent.getType());
    mediaContentFromDb.setFullContent(mediaContent.getFullContent());
    mediaContentService.save(mediaContent);
  }

  @DeleteMapping("/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteMediaContent(@PathVariable long mediaId) {
    mediaContentService.deleteById(mediaId);
  }

  private MediaContentDto convertMediaContentEntityToDto(MediaContent mediaContentEntity){
    MediaContentDto mediaContentDto = new MediaContentDto();
    mediaContentDto.setId(mediaContentEntity.getId());
    mediaContentDto.setName(mediaContentEntity.getName());
    mediaContentDto.setType(mediaContentEntity.getType());
    mediaContentDto.setFullContent(mediaContentEntity.getFullContent());
    return mediaContentDto;
  }

  private MediaContent convertMediaContentDtoToEntity(MediaContentDto mediaContentDto){
    MediaContent mediaContent = new MediaContent();
    mediaContent.setId(mediaContentDto.getId());
    mediaContent.setName(mediaContentDto.getName());
    mediaContent.setType(mediaContentDto.getType());
    mediaContent.setFullContent(mediaContentDto.getFullContent());
    return mediaContent;
  }
}
