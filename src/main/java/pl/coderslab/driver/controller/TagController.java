package pl.coderslab.driver.controller;

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
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.service.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Tag> getAllTags() {
    return tagService.findAll();
  }

  @GetMapping("/{tagId}")
  @ResponseStatus(HttpStatus.OK)
  public Tag getTagById(@PathVariable long tagId) {
    return Optional.ofNullable(tagService.findById(tagId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createTag(@RequestBody Tag tag) {
    tagService.save(tag);
  }

  @PutMapping("/{tagId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateTag(@PathVariable long tagId, @RequestBody Tag tag) {
    Tag tagToUpdate = tagService.findById(tagId);
    tagToUpdate.setName(tag.getName());
    tagService.save(tag);
  }

  @DeleteMapping("/{tagId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTag(@PathVariable long tagId) {
    tagService.deleteById(tagId);
  }
}
