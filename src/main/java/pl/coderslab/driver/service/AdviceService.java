package pl.coderslab.driver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.repository.AdviceRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdviceService {

  private final AdviceRepository adviceRepository;

  public List<Advice> findAll(){
    return adviceRepository.findAll();
  }

  public Optional<Advice> findById(Long id){
    return adviceRepository.findById(id);
  }

  public void save(Advice advice){
    adviceRepository.save(advice);
  }

  public void deleteById(Long id){
    adviceRepository.deleteById(id);
  }


}
