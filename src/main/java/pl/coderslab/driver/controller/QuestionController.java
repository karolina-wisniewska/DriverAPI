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
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.model.QuestionDto;
import pl.coderslab.driver.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<QuestionDto> getAllQuestions() {
    return questionService.findAll()
            .stream()
            .map(this::convertQuestionEntityToDto)
            .collect(Collectors.toList());
  }

  @GetMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public QuestionDto getQuestionById(@PathVariable long questionId) {
    return Optional.ofNullable(questionService.findById(questionId))
            .map(this::convertQuestionEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createQuestion(@RequestBody QuestionDto question) {
    questionService.save(convertQuestionDtoToEntity(question));
  }

  @PutMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateQuestion(@PathVariable long questionId, @RequestBody QuestionDto question) {
    Question questionFromDb = questionService.findById(questionId);
    Question updatedQuestion = convertQuestionDtoToEntity(question);
    questionFromDb.setContent(updatedQuestion.getContent());
    questionFromDb.setAnswers(updatedQuestion.getAnswers());
    questionFromDb.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
    questionService.save(questionFromDb);
  }

  @DeleteMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteQuestion(@PathVariable long questionId) {
    questionService.deleteById(questionId);
  }

  private QuestionDto convertQuestionEntityToDto(Question questionEntity){
    QuestionDto questionDto = new QuestionDto();
    questionDto.setId(questionEntity.getId());
    questionDto.setContent(questionEntity.getContent());
    questionDto.setAnswers(questionEntity.getAnswers());
    questionDto.setCorrectAnswer(questionEntity.getCorrectAnswer());
    return questionDto;
  }

  private Question convertQuestionDtoToEntity(QuestionDto questionDto){
    Question question = new Question();
    question.setId(questionDto.getId());
    question.setContent(questionDto.getContent());
    question.setAnswers(questionDto.getAnswers());
    question.setCorrectAnswer(questionDto.getCorrectAnswer());
    return question;
  }
}


