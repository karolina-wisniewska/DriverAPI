package pl.coderslab.driver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.service.AdviceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/advices")
@RequiredArgsConstructor
public class AdviceController {

  private final AdviceService adviceService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Advice> getAllAdvices() {
    return adviceService.findAll();
  }

  @GetMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public Advice getAdviceById(@PathVariable long adviceId) {
    return adviceService.findById(adviceId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/test/{adviceId}")
  public ResponseEntity<Advice> getAdviceTestById(@PathVariable long adviceId) {
    Optional<Advice> advice = adviceService.findById(adviceId);
    return advice.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAdvice(@RequestBody Advice advice) {
    adviceService.save(advice);
  }

  @PutMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateAdvice(@PathVariable long adviceId, @RequestBody Advice advice) {
    Advice adviceToUpdate = adviceService.findById(adviceId).orElseThrow(EntityNotFoundException::new);
    adviceToUpdate.setName(advice.getName());
    adviceToUpdate.setDescription(advice.getDescription());
    adviceToUpdate.setMediaContent(advice.getMediaContent());
    adviceToUpdate.setTags(advice.getTags());
    adviceService.save(advice);
  }

  @DeleteMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAdvice(@PathVariable long adviceId) {
    adviceService.deleteById(adviceId);
  }
}
