package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.entity.Training;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.repository.TrainingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingService {
  private final TrainingRepository trainingRepository;
  private final QuestionService questionService;
  private final UserParamsService userParamsService;
  private final AdviceService adviceService;

  public List<Training> findAll() {
    return trainingRepository.findAll();
  }

  public Optional<Training> findById(Long id) {
    return trainingRepository.findById(id);
  }

  public Training update(Training updatedTraining){
    return trainingRepository.save(updatedTraining);
  }

  public Training save(Training training) {
    return trainingRepository.save(training);
  }

  public void deleteById(Long id) {
    trainingRepository.deleteById(id);
  }

  public int calculatePointsFromTraining(Map<Long,Long> answersMap) {
    int points = 0;
    for (Long key : answersMap.keySet()) {
      Long userAnswerId = answersMap.get(key);
      if (questionService.isQuestionPassed(key, userAnswerId)) {
        points++;
      }
    }
    return points;
  }

  public int checkTraining(Long trainingId, User user, Map<Long,Long> answersMap) {
    int points = calculatePointsFromTraining(answersMap);
    List<Question> trainingQuestions = questionService.findAllByTraining(trainingId);
    UserParams userParams = userParamsService.findByUser(user).orElseThrow(EntityNotFoundException::new);
    List<Advice> passedAdvices = userParams.getPassedAdvices();
    Advice advice = adviceService.findById(trainingId).orElseThrow(EntityNotFoundException::new);
    if (!passedAdvices.contains(advice)) {
      if (points > 0) {
        userParamsService.addPoints(user, points);
        if (points == trainingQuestions.size()) {
          userParamsService.passTraining(user, trainingId);
        }
      }
    }
    return points;
  }

}
