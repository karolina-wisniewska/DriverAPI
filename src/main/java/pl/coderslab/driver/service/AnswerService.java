package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Answer;
import pl.coderslab.driver.repository.AnswerRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

  private final AnswerRepository answerRepository;

  public List<Answer> findAll(){
    return answerRepository.findAll();
  }

  public List<Answer> findAllByQuestion(Long questionId){
    return answerRepository.findByQuestion(questionId);
  }

  public Answer findById(Long id){
    return answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(Answer answer){
    answerRepository.save(answer);
  }

  public void deleteById(Long id){
    answerRepository.deleteById(id);
  }


}
