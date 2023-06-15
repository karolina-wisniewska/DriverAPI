package pl.coderslab.driver.converter;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Training;
import pl.coderslab.driver.model.TrainingDto;
import pl.coderslab.driver.service.AdviceService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrainingConverter {

  private final QuestionConverter questionConverter;
  private final AdviceService adviceService;

  public TrainingDto convertTrainingEntityToDto(Training trainingEntity){
    TrainingDto trainingDto = new TrainingDto();
    trainingDto.setId(trainingEntity.getId());
    Advice advice = trainingEntity.getAdvice();
    trainingDto.setAdviceId(advice.getId());
    trainingDto.setQuestions(questionConverter.convertListQuestionEntityToDto(trainingEntity.getQuestions()));
    return trainingDto;
  }

  public Training convertTrainingDtoToEntity(TrainingDto trainingDto){
    Training training = new Training();
    training.setId(trainingDto.getId());
    training.setAdvice(adviceService.findById(trainingDto.getAdviceId())
            .orElseThrow(EntityNotFoundException::new));
    training.setQuestions(questionConverter.convertListQuestionDtoToEntity(trainingDto.getQuestions()));
    return training;
  }

  public List<TrainingDto> convertListTrainingEntityToDto(List<Training> trainings){
    return trainings
            .stream()
            .map(this::convertTrainingEntityToDto)
            .collect(Collectors.toList());
  }

  public List<Training> convertListTrainingDtoToEntity(List<TrainingDto> trainingsDto){
    return trainingsDto
            .stream()
            .map(this::convertTrainingDtoToEntity)
            .collect(Collectors.toList());
  }

}
