package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import pl.coderslab.driver.converter.AdviceConverter;
import pl.coderslab.driver.converter.TagConverter;
import pl.coderslab.driver.converter.UserConverter;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.model.AdviceDto;
import pl.coderslab.driver.model.AdviceToSaveDto;
import pl.coderslab.driver.service.AdviceService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/advices")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class AdviceController {

  private final AdviceService adviceService;
  private final AdviceConverter adviceConverter;
  private final TagConverter tagConverter;
  private final UserConverter userConverter;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> getAllAdvices(Principal principal) {
    return adviceConverter.convertListAdviceEntityToDto(principal, adviceService.findAll());
  }

  @GetMapping(params = "tag")
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> getAllAdvicesByTag(Principal principal, @RequestParam Tag tag) {
    return adviceConverter.convertListAdviceEntityToDto(principal, adviceService.findAllByTag(tag));
  }

  @GetMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public AdviceDto getAdviceById(Principal principal, @PathVariable long adviceId) {
    return adviceService.findById(adviceId)
            .map(entity -> adviceConverter.convertAdviceEntityToDto(entity, userConverter.getUserFromPrincipal(principal)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/advice-of-the-week")
  @ResponseStatus(HttpStatus.OK)
  public AdviceDto getAdviceOfTheWeek(Principal principal) {
    long index = adviceService.getAdviceOfTheWeekIndex();
    return adviceService.findById(index)
            .map(entity -> adviceConverter.convertAdviceEntityToDto(entity, userConverter.getUserFromPrincipal(principal)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/discover-others/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> discoverOthersById(Principal principal, @PathVariable long adviceId) {
    return adviceConverter.convertListAdviceEntityToDto(principal, adviceService.discoverOthers(adviceId));
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAdvice(@RequestBody AdviceToSaveDto advice) {
    Advice adviceEntity = adviceConverter.convertAdviceToSaveDtoToEntity(advice);
    adviceEntity.setLikes(0L);
    adviceEntity.setShares(0L);
    adviceService.save(adviceEntity);
  }

  @PutMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateAdvice(@PathVariable long adviceId, @RequestBody AdviceToSaveDto updatedAdvice) {
    Advice adviceFromDb = adviceService.findById(adviceId).orElseThrow(EntityNotFoundException::new);
    adviceFromDb.setName(updatedAdvice.getName());
    adviceFromDb.setMediaId(updatedAdvice.getMediaId());
    adviceFromDb.setDescription(updatedAdvice.getDescription());
    adviceFromDb.setTags(tagConverter.convertListTagDtoToEntity(updatedAdvice.getTags()));
    adviceService.save(adviceFromDb);
  }

  @DeleteMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAdvice(@PathVariable long adviceId) {
    adviceService.deleteById(adviceId);
  }

}
