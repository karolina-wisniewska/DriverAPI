package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Answer;
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.repository.QuestionRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;

  public List<Question> findAll(){
    return questionRepository.findAll();
  }

  public Question findById(Long id){
    return questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(Question question){
    questionRepository.save(question);
  }

  public void deleteById(Long id){
    questionRepository.deleteById(id);
  }

  public List<Question> findAllByTraining(Long trainingId){
    return questionRepository.findAllByTraining(trainingId);
  }

  public boolean isQuestionPassed(Question question, Answer answer){
    Answer correctAnswer = question.getCorrectAnswer();
    return answer.equals(correctAnswer);
  }

}
