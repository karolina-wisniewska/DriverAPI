package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.repository.AdviceRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdviceService {

  private final AdviceRepository adviceRepository;

  public List<Advice> findAll(){
    return adviceRepository.findAll();
  }

  public Advice findById(Long id){
    return adviceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void save(Advice advice){
    adviceRepository.save(advice);
  }

  public void update(Advice advice) {adviceRepository.save(advice);}

  public void deleteById(Long id){
    adviceRepository.deleteById(id);
  }


}
