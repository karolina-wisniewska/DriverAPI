package pl.coderslab.driver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.entity.security.User;
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

  public List<Advice> findAll() {
    return adviceRepository.findAll();
  }

  public List<Advice> discoverOthers(Long adviceId) {
    Advice advice = findById(adviceId).orElseThrow(EntityNotFoundException::new);
    return adviceRepository.findAll()
            .stream()
            .filter(a -> !a.equals(advice))
            .collect(Collectors.toList());
  }

  public List<Advice> findAdvicesToDo(List<Advice> passedAdvices) {
    List<Advice> advicesToDo = findAll();
    advicesToDo.removeAll(passedAdvices);
    return advicesToDo;
  }

  public List<Advice> findAllByUser(User user) {
    return adviceRepository.findAllByUser(user);
  }

  public List<Advice> findAllByTag(Tag tag) {
    return adviceRepository.findByTag(tag);
  }

  public Optional<Advice> findById(Long id) {
    return adviceRepository.findById(id);
  }

  public void save(Advice advice) {
    adviceRepository.save(advice);
  }

  public void deleteById(Long id) {
    adviceRepository.deleteById(id);
  }

  public long count() {
    return adviceRepository.count();
  }

  public void share(Long adviceId) {
    Advice advice = findById(adviceId).orElseThrow(EntityNotFoundException::new);
    Long shares = advice.getShares();
    shares++;
    advice.setShares(shares);
    adviceRepository.save(advice);
  }

  public void like(Long adviceId) {
    Advice advice = findById(adviceId).orElseThrow(EntityNotFoundException::new);
    Long likes = advice.getLikes();
    likes++;
    advice.setLikes(likes);
    adviceRepository.save(advice);
  }

  public long getAdviceOfTheWeekIndex() {
    LocalDate date = LocalDate.now();
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
    int year = date.getYear();
    long numberOfAdvices = count();
    return indexCalculator.calculateIndex(weekNumber, year, numberOfAdvices);
  }

  public boolean checkIfAdvicePassed(Long adviceId, User user){
    Advice advice = findById(adviceId).orElseThrow(EntityNotFoundException::new);
    return findAllByUser(user).contains(advice);
  };
}
