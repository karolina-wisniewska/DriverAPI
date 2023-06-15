package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.converter.TagConverter;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.model.TagDto;
import pl.coderslab.driver.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;
  private final TagConverter tagConverter;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TagDto> getAllTags() {
    return tagConverter.convertListTagEntityToDto(tagService.findAll());
  }

  @GetMapping("/{tagId}")
  @ResponseStatus(HttpStatus.OK)
  public TagDto getTagById(@PathVariable long tagId) {
    return tagService.findById(tagId)
            .map(tagConverter::convertTagEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping(params = "search")
  @ResponseStatus(HttpStatus.OK)
  public TagDto getTagByName(@RequestParam String search) {
    return tagService.findByName(search)
            .map(tagConverter::convertTagEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createTag(@RequestBody TagDto tag) {
    tagService.save(tagConverter.convertTagDtoToEntity(tag));
  }

  @PutMapping("/{tagId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateTag(@PathVariable long tagId, @RequestBody Tag updatedTag) {
    Tag tagFromDb = tagService.findById(tagId).orElseThrow(EntityNotFoundException::new);
    tagFromDb.setName(updatedTag.getName());
    tagService.save(tagFromDb);
  }

  @DeleteMapping("/{tagId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTag(@PathVariable long tagId) {
    tagService.deleteById(tagId);
  }

}
