package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.repository.AdviceRepository;
import pl.coderslab.driver.util.IndexCalculator;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdviceService {

  private final AdviceRepository adviceRepository;
  private final IndexCalculator indexCalculator;

  public List<Advice> findAll(){
    return adviceRepository.findAll();
  }

  public List<Advice> discoverOthers(Long adviceId){
    Advice advice = findById(adviceId).orElseThrow(EntityNotFoundException::new);
    return adviceRepository.findAll()
            .stream()
            .filter(a -> !a.equals(advice))
            .collect(Collectors.toList());
  }

  public List<Advice> findAllByTag(Tag tag){
    return adviceRepository.findByTag(tag);
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

  public long count(){
    return adviceRepository.count();
  }

  public long getAdviceOfTheWeekIndex(){
    LocalDate date = LocalDate.now();
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
    int year = date.getYear();
    long numberOfAdvices = count();
    return indexCalculator.calculateIndex(weekNumber, year, numberOfAdvices);
  }
}
