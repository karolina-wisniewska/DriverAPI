package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Answer;
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.entity.Training;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.repository.TrainingRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingService {
  private final TrainingRepository trainingRepository;
  private final QuestionService questionService;
  private final AnswerService answerService;
  private final UserParamsService userParamsService;

  private final AdviceService adviceService;

  public List<Training> findAll() {
    return trainingRepository.findAll();
  }

  public Training findById(Long id) {
    return trainingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(Training training) {
    trainingRepository.save(training);
  }

  public void deleteById(Long id) {
    trainingRepository.deleteById(id);
  }

  public int calculatePointsFromTraining(Long trainingId, List<Long> answers) {
    int points = 0;
    List<Question> questions = questionService.findAllByTraining(trainingId);
    for (int i = 0; i < answers.size(); i++) {
      Answer answer = answerService.findById(answers.get(i));
      if (questionService.isQuestionPassed(questions.get(i), answer)) {
        points++;
      }
    }
    return points;
  }

  public void checkTraining(Long trainingId, User user, List<Long> answers) {
    int points = calculatePointsFromTraining(trainingId, answers);
    UserParams userParams = userParamsService.findByUser(user).orElseThrow(EntityNotFoundException::new);
    List<Advice> passedAdvices = userParams.getPassedAdvices();
    Advice advice = adviceService.findById(trainingId).orElseThrow(EntityNotFoundException::new);
    if (!passedAdvices.contains(advice)) {
      if (points > 0) {
        userParamsService.addPoints(user, points);
        if (points == answers.size()) {
          userParamsService.passTraining(user, trainingId);
        }

      }
    }
  }

}
