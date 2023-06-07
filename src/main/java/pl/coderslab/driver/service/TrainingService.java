package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Training;
import pl.coderslab.driver.repository.TrainingRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingService {

  private final TrainingRepository trainingRepository;

  public List<Training> findAll(){
    return trainingRepository.findAll();
  }

  public Training findById(Long id){
    return trainingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(Training training){
    trainingRepository.save(training);
  }

  public void deleteById(Long id){
    trainingRepository.deleteById(id);
  }

}
