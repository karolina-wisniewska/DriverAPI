package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.repository.UserParamsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserParamsService {

  private final UserParamsRepository userParamsRepository;
  private final AdviceService adviceService;

  public List<UserParams> findAll(){
    return userParamsRepository.findAll();
  }

  public UserParams findById(Long id){
    return userParamsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public Optional<UserParams> findByUser(User user){
    return userParamsRepository.findByUser(user);
  }


  public void save(UserParams userParams){
    userParamsRepository.save(userParams);
  }

  public void deleteById(Long id){
    userParamsRepository.deleteById(id);
  }

  public void addPoints(User user, Integer points) {
    UserParams userParams = findByUser(user).orElseThrow(EntityNotFoundException::new);
    userParams.setPoints(userParams.getPoints()+points);
  }

  public void passTraining(User user, Long trainingId) {
    UserParams userParams = findByUser(user).orElseThrow(EntityNotFoundException::new);
    List<Advice> passedAdvices = userParams.getPassedAdvices();
    passedAdvices.add(adviceService.findById(trainingId).orElseThrow(EntityNotFoundException::new));
    userParams.setPassedAdvices(passedAdvices);
  }


}
