package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
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
import pl.coderslab.driver.model.AdviceDto;
import pl.coderslab.driver.model.AdviceToSaveDto;
import pl.coderslab.driver.service.AdviceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/advices")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class AdviceController {

  private final AdviceService adviceService;
  private final ServletWebServerApplicationContext applicationContext;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> getAllAdvices() {
    return adviceService.findAll()
            .stream()
            .map(this::convertAdviceEntityToDto)
            .collect(Collectors.toList());
  }

  @GetMapping(params = "tag")
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> getAllAdvicesByTag(@RequestParam Tag tag) {
    return adviceService.findAllByTag(tag)
            .stream()
            .map(this::convertAdviceEntityToDto)
            .collect(Collectors.toList());
  }

  @GetMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public AdviceDto getAdviceById(@PathVariable long adviceId) {
    return adviceService.findById(adviceId)
            .map(this::convertAdviceEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/advice-of-the-week")
  @ResponseStatus(HttpStatus.OK)
  public AdviceDto getAdviceOfTheWeek() {
    long index = adviceService.getAdviceOfTheWeekIndex();
    return adviceService.findById(index)
            .map(this::convertAdviceEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  @GetMapping("/discover-others/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> discoverOthersById(@PathVariable long adviceId) {
    return adviceService.discoverOthers(adviceId)
            .stream()
            .map(this::convertAdviceEntityToDto)
            .collect(Collectors.toList());
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
    Advice adviceEntity = convertAdviceToSaveDtoToEntity(advice);
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
    adviceFromDb.setTags(updatedAdvice.getTags());
    adviceService.save(adviceFromDb);
  }

  @DeleteMapping("/{adviceId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAdvice(@PathVariable long adviceId) {
    adviceService.deleteById(adviceId);
  }

  private AdviceDto convertAdviceEntityToDto(Advice adviceEntity){
    AdviceDto adviceDto = new AdviceDto();
    adviceDto.setId(adviceEntity.getId());
    adviceDto.setName(adviceEntity.getName());
    adviceDto.setMediaId(adviceEntity.getMediaId());
    adviceDto.setMediaUrl(getMediaUrlFromMediaId(adviceEntity.getMediaId()));
    adviceDto.setDescription(adviceEntity.getDescription());
    adviceDto.setTags(adviceEntity.getTags());
    adviceDto.setShares(adviceEntity.getShares());
    adviceDto.setLikes(adviceEntity.getLikes());
    return adviceDto;
  }

  private Advice convertAdviceToSaveDtoToEntity(AdviceToSaveDto adviceToSaveDto){
    Advice adviceEntity = new Advice();
    adviceEntity.setId(adviceToSaveDto.getId());
    adviceEntity.setName(adviceToSaveDto.getName());
    adviceEntity.setMediaId(adviceToSaveDto.getMediaId());
    adviceEntity.setDescription(adviceToSaveDto.getDescription());
    adviceEntity.setTags(adviceToSaveDto.getTags());
    return adviceEntity;
  }

  private String getMediaUrlFromMediaId(Long mediaId){
    Integer port = applicationContext.getWebServer().getPort();
    return "localhost:" + port + "/api/media/" + mediaId;
  }
}
