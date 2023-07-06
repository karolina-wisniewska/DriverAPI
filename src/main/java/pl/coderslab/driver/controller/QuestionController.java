package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import pl.coderslab.driver.converter.AnswerConverter;
import pl.coderslab.driver.converter.QuestionConverter;
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.model.QuestionDto;
import pl.coderslab.driver.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;
  private final QuestionConverter questionConverter;
  private final AnswerConverter answerConverter;

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<QuestionDto> getAllQuestions() {
    return questionConverter.convertListQuestionEntityToDto(questionService.findAll());
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @GetMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public QuestionDto getQuestionById(@PathVariable long questionId) {
    return Optional.ofNullable(questionService.findById(questionId))
            .map(questionConverter::convertQuestionEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createQuestion(@RequestBody QuestionDto question) {
    questionService.save(questionConverter.convertQuestionDtoToEntity(question));
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @PutMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateQuestion(@PathVariable long questionId, @RequestBody QuestionDto updatedQuestion) {
    Question questionFromDb = questionService.findById(questionId);
    questionFromDb.setContent(updatedQuestion.getContent());
    questionFromDb.setAnswers(answerConverter.convertListAnswerDtoToEntity(updatedQuestion.getAnswers()));
    questionFromDb.setCorrectAnswer(answerConverter.convertAnswerDtoToEntity(updatedQuestion.getCorrectAnswer()));
    questionService.save(questionFromDb);
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @DeleteMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteQuestion(@PathVariable long questionId) {
    questionService.deleteById(questionId);
  }


}


