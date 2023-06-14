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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.entity.Answer;
import pl.coderslab.driver.model.AnswerDto;
import pl.coderslab.driver.service.AnswerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class AnswerController {

  private final AnswerService answerService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AnswerDto> getAllAnswers() {
    return answerService.findAll()
            .stream()
            .map(this::convertAnswerEntityToDto)
            .collect(Collectors.toList());
  }

  @GetMapping(params = "questionId")
  @ResponseStatus(HttpStatus.OK)
  public List<AnswerDto> getAllAnswersByQuestion(@RequestParam Long questionId) {
    return answerService.findAllByQuestion(questionId)
            .stream()
            .map(this::convertAnswerEntityToDto)
            .collect(Collectors.toList());
  }

  @GetMapping("/{answerId}")
  @ResponseStatus(HttpStatus.OK)
  public AnswerDto getAnswerById(@PathVariable long answerId) {
    return Optional.ofNullable(answerService.findById(answerId))
            .map(this::convertAnswerEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAnswer(@RequestBody AnswerDto answer) {
    answerService.save(convertAnswerDtoToEntity(answer));
  }

  @PutMapping("/{answerId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateAnswer(@PathVariable long answerId, @RequestBody AnswerDto updatedAnswer) {
    Answer answerFromDb = answerService.findById(answerId);
    answerFromDb.setContent(convertAnswerDtoToEntity(updatedAnswer).getContent());
    answerService.save(answerFromDb);
  }

  @DeleteMapping("/{answerId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAnswer(@PathVariable long answerId) {
    answerService.deleteById(answerId);
  }


  private AnswerDto convertAnswerEntityToDto(Answer answerEntity){
    AnswerDto answerDto = new AnswerDto();
    answerDto.setId(answerEntity.getId());
    answerDto.setContent(answerEntity.getContent());
    return answerDto;
  }

  private Answer convertAnswerDtoToEntity(AnswerDto answerDto){
    Answer answer = new Answer();
    answer.setId(answerDto.getId());
    answer.setContent(answerDto.getContent());
    return answer;
  }
}
