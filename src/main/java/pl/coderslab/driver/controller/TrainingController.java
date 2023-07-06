package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
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
import pl.coderslab.driver.converter.QuestionConverter;
import pl.coderslab.driver.converter.TrainingConverter;
import pl.coderslab.driver.converter.UserConverter;
import pl.coderslab.driver.entity.Training;
import pl.coderslab.driver.model.TrainingDto;
import pl.coderslab.driver.model.TrainingToCheckDto;
import pl.coderslab.driver.service.AdviceService;
import pl.coderslab.driver.service.TrainingService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class TrainingController {

  private final TrainingService trainingService;
  private final AdviceService adviceService;
  private final TrainingConverter trainingConverter;
  private final QuestionConverter questionConverter;
  private final UserConverter userConverter;


  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TrainingDto> getAllTrainings() {
    return trainingConverter.convertListTrainingEntityToDto(trainingService.findAll());
  }

  @GetMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public TrainingDto getTrainingById(@PathVariable long trainingId) {
    return trainingService.findById(trainingId)
            .map(trainingConverter::convertTrainingEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createTraining(@RequestBody TrainingDto training) {
    trainingService.save(trainingConverter.convertTrainingDtoToEntity(training));
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @PutMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateTraining(@PathVariable long trainingId, @RequestBody TrainingDto updatedTraining) {
    Training trainingFromDb = trainingService.findById(trainingId).orElseThrow(EntityNotFoundException::new);
    trainingFromDb.setQuestions(questionConverter.convertListQuestionDtoToEntity(updatedTraining.getQuestions()));
    trainingFromDb.setAdvice(adviceService.findById(trainingId)
            .orElseThrow(EntityNotFoundException::new));
    trainingService.save(trainingFromDb);
  }

  @Operation(summary = "Admin access only")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @DeleteMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTraining(@PathVariable long trainingId) {
    trainingService.deleteById(trainingId);
  }

  @PostMapping("/check/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public int checkTestTraining(Principal principal, @PathVariable Long trainingId, @RequestBody TrainingToCheckDto trainingToCheckDto) {
    return trainingService.checkTraining(trainingId, userConverter.getUserFromPrincipal(principal), trainingToCheckDto.getQuestionAnswerMap());
  }

}
