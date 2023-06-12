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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.entity.Training;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.service.TrainingService;
import pl.coderslab.driver.service.UserParamsService;
import pl.coderslab.driver.service.security.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
public class TrainingController {

  private final TrainingService trainingService;
  private final UserService userService;
  private final UserParamsService userParamsService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Training> getAllTrainings() {
    return trainingService.findAll();
  }

  @GetMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public Training getTrainingById(@PathVariable long trainingId) {
    return Optional.ofNullable(trainingService.findById(trainingId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createTraining(@RequestBody Training training) {
    trainingService.save(training);
  }

  @PutMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateTraining(@PathVariable long trainingId, @RequestBody Training training) {
    Training trainingToUpdate = trainingService.findById(trainingId);
    trainingToUpdate.setQuestions(training.getQuestions());
    trainingService.save(training);
  }

  @DeleteMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTraining(@PathVariable long trainingId) {
    trainingService.deleteById(trainingId);
  }

  @PostMapping("/check/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public void checkTraining(Principal principal, @PathVariable Long trainingId, @RequestParam Long answer1, @RequestParam Long answer2, @RequestParam Long answer3) {
    List<Long> answers = new ArrayList<>();
    answers.add(answer1);
    answers.add(answer2);
    answers.add(answer3);
    String username = principal.getName();
    User user = userService.findUserByUserName(username);
    trainingService.checkTraining(trainingId, user, answers);
  }
}
