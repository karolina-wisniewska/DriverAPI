package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.repository.UserParamsRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserParamsService {

  private final UserParamsRepository userParamsRepository;

  public List<UserParams> findAll(){
    return userParamsRepository.findAll();
  }

  public UserParams findById(Long id){
    return userParamsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(UserParams userParams){
    userParamsRepository.save(userParams);
  }

  public void deleteById(Long id){
    userParamsRepository.deleteById(id);
  }


}
