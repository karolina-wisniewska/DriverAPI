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
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Question> getAllQuestions() {
    return questionService.findAll();
  }

  @GetMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public Question getQuestionById(@PathVariable long questionId) {
    return Optional.ofNullable(questionService.findById(questionId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createQuestion(@RequestBody Question question) {
    questionService.save(question);
  }

  @PutMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateQuestion(@PathVariable long questionId, @RequestBody Question question) {
    Question questionToUpdate = questionService.findById(questionId);
    questionToUpdate.setAnswers(question.getAnswers());
    questionService.save(question);
  }

  @DeleteMapping("/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteQuestion(@PathVariable long questionId) {
    questionService.deleteById(questionId);
  }
}
