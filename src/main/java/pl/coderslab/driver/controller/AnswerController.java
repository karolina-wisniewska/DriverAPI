package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
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
import pl.coderslab.driver.converter.AnswerConverter;
import pl.coderslab.driver.entity.Answer;
import pl.coderslab.driver.model.AnswerDto;
import pl.coderslab.driver.service.AnswerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/answers")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class AnswerController {

  private final AnswerService answerService;
  private final AnswerConverter answerConverter;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AnswerDto> getAllAnswers() {
    return answerConverter.convertListAnswerEntityToDto(answerService.findAll());
  }

  @GetMapping(params = "questionId")
  @ResponseStatus(HttpStatus.OK)
  public List<AnswerDto> getAllAnswersByQuestion(@Nullable @RequestParam Long questionId) {
    return answerConverter.convertListAnswerEntityToDto(answerService.findAllByQuestion(questionId));
  }

  @GetMapping("/{answerId}")
  @ResponseStatus(HttpStatus.OK)
  public AnswerDto getAnswerById(@PathVariable long answerId) {
    return Optional.ofNullable(answerService.findById(answerId))
            .map(answerConverter::convertAnswerEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAnswer(@RequestBody AnswerDto answer) {
    answerService.save(answerConverter.convertAnswerDtoToEntity(answer));
  }

  @PutMapping("/{answerId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateAnswer(@PathVariable long answerId, @RequestBody AnswerDto updatedAnswer) {
    Answer answerFromDb = answerService.findById(answerId);
    answerFromDb.setContent(answerConverter.convertAnswerDtoToEntity(updatedAnswer).getContent());
    answerService.save(answerFromDb);
  }

  @DeleteMapping("/{answerId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAnswer(@PathVariable long answerId) {
    answerService.deleteById(answerId);
  }



}
