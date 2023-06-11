package pl.coderslab.driver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.model.FullAdviceDto;
import pl.coderslab.driver.model.ListAdviceDto;
import pl.coderslab.driver.service.AdviceService;
import pl.coderslab.driver.service.security.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/advices")
@RequiredArgsConstructor
public class AdviceController {

  private final AdviceService adviceService;
  private final UserService userService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ListAdviceDto> getAllAdvices() {
    return adviceService.findAll()
            .stream()
            .map(this::convertAdviceToListAdviceDto)
            .collect(Collectors.toList());
  }

  @GetMapping(params = "tag")
  @ResponseStatus(HttpStatus.OK)
  public List<ListAdviceDto> getAllAdvicesByTag(@RequestParam Tag tag) {
    return adviceService.findAllByTag(tag)
            .stream()
            .map(this::convertAdviceToListAdviceDto)
            .collect(Collectors.toList());
  }

  @GetMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public FullAdviceDto getAdviceById(@PathVariable long adviceId) {
    return adviceService.findById(adviceId)
            .map(this::convertAdviceToFullAdviceDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/advice-of-the-week")
  @ResponseStatus(HttpStatus.OK)
  public FullAdviceDto getAdviceOfTheWeek() {
    long index = adviceService.getAdviceOfTheWeekIndex();
    return adviceService.findById(index)
            .map(this::convertAdviceToFullAdviceDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/discover-others/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ListAdviceDto> discoverOthersById(@PathVariable long adviceId) {
    return adviceService.discoverOthers(adviceId)
            .stream()
            .map(this::convertAdviceToListAdviceDto)
            .collect(Collectors.toList());
  }

  @GetMapping("/to-do")
  @ResponseStatus(HttpStatus.OK)
  public List<ListAdviceDto> getAdvicesToDo(Principal principal) {
    String username = principal.getName();
    User user = userService.findUserByUserName(username);
    List<Advice> passedAdvices = adviceService.findAllByUser(user);
    return adviceService.findAdvicesToDo(passedAdvices)
            .stream()
            .map(this::convertAdviceToListAdviceDto)
            .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAdvice(@RequestBody Advice advice) {
    adviceService.save(advice);
  }

  @PostMapping("/share/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void shareAdvice(@PathVariable Long adviceId) {
    adviceService.share(adviceId);
  }

  @PostMapping("/like/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void likeAdvice(@PathVariable Long adviceId) {
    adviceService.like(adviceId);
  }

  @PutMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateAdvice(@PathVariable long adviceId, @RequestBody Advice advice) {
    Advice adviceToUpdate = adviceService.findById(adviceId).orElseThrow(EntityNotFoundException::new);
    adviceToUpdate.setName(advice.getName());
    adviceToUpdate.setDescription(advice.getDescription());
    adviceToUpdate.setMediaContent(advice.getMediaContent());
    adviceToUpdate.setTags(advice.getTags());
    adviceToUpdate.setLikes(advice.getLikes());
    adviceService.save(advice);
  }

  @DeleteMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAdvice(@PathVariable long adviceId) {
    adviceService.deleteById(adviceId);
  }

  private ListAdviceDto convertAdviceToListAdviceDto(Advice adviceEntity){
    ListAdviceDto listAdviceDto = new ListAdviceDto();
    listAdviceDto.setId(adviceEntity.getId());
    listAdviceDto.setName(adviceEntity.getName());
    listAdviceDto.setDescription(adviceEntity.getDescription());
    listAdviceDto.setTags(adviceEntity.getTags());
    listAdviceDto.setCover(adviceEntity.getMediaContent().getCover());
    return listAdviceDto;
  }

  private FullAdviceDto convertAdviceToFullAdviceDto(Advice adviceEntity){
    FullAdviceDto fullAdviceDto = new FullAdviceDto();
    fullAdviceDto.setId(adviceEntity.getId());
    fullAdviceDto.setName(adviceEntity.getName());
    fullAdviceDto.setDescription(adviceEntity.getDescription());
    fullAdviceDto.setTags(adviceEntity.getTags());
    fullAdviceDto.setFullContent(adviceEntity.getMediaContent().getFullContent());
    fullAdviceDto.setShares(adviceEntity.getShares());
    fullAdviceDto.setLikes(adviceEntity.getLikes());
    return fullAdviceDto;
  }

}
