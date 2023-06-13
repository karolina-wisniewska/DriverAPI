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
import pl.coderslab.driver.service.MediaContentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mediaContents")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class MediaContentController {

  private final MediaContentService mediaContentService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<MediaContent> getAllMediaContents() {
    return mediaContentService.findAll();
  }

  @GetMapping("/{mediaContentId}")
  @ResponseStatus(HttpStatus.OK)
  public MediaContent getMediaContentById(@PathVariable long mediaContentId) {
    return Optional.ofNullable(mediaContentService.findById(mediaContentId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createMediaContent(@RequestBody MediaContent mediaContent) {
    mediaContentService.save(mediaContent);
  }

  @PutMapping("/{mediaContentId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateMediaContent(@PathVariable long mediaContentId, @RequestBody MediaContent mediaContent) {
    MediaContent mediaContentToUpdate = mediaContentService.findById(mediaContentId);
    mediaContentToUpdate.setCover(mediaContent.getCover());
    mediaContentService.save(mediaContent);
  }

  @DeleteMapping("/{mediaContentId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteMediaContent(@PathVariable long mediaContentId) {
    mediaContentService.deleteById(mediaContentId);
  }
}
