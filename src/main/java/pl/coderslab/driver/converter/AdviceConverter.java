package pl.coderslab.driver.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.model.AdviceDto;
import pl.coderslab.driver.model.AdviceToSaveDto;
import pl.coderslab.driver.model.TagDto;
import pl.coderslab.driver.service.AdviceService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdviceConverter {

  private final AdviceService adviceService;
  private final UserConverter userConverter;
  private final TagConverter tagConverter;

  public AdviceDto convertAdviceEntityToDto(Advice adviceEntity, User user) {
    AdviceDto adviceDto = new AdviceDto();
    adviceDto.setId(adviceEntity.getId());
    adviceDto.setName(adviceEntity.getName());
    adviceDto.setMediaUrl(getMediaUrlFromMediaId(adviceEntity.getMediaId()));
    adviceDto.setDescription(adviceEntity.getDescription());
    List<TagDto> tagsDto = adviceEntity.getTags()
            .stream()
            .map(tagConverter::convertTagEntityToDto)
            .collect(Collectors.toList());
    adviceDto.setTags(tagsDto);
    adviceDto.setShares(adviceEntity.getShares());
    adviceDto.setLikes(adviceEntity.getLikes());
    adviceDto.setPassed(adviceService.checkIfAdvicePassed(adviceEntity.getId(), user));
    return adviceDto;
  }

  public Advice convertAdviceToSaveDtoToEntity(AdviceToSaveDto adviceToSaveDto) {
    Advice adviceEntity = new Advice();
    adviceEntity.setId(adviceToSaveDto.getId());
    adviceEntity.setName(adviceToSaveDto.getName());
    adviceEntity.setMediaId(adviceToSaveDto.getMediaId());
    adviceEntity.setDescription(adviceToSaveDto.getDescription());
    List<Tag> tags = adviceToSaveDto.getTags()
            .stream()
            .map(tagConverter::convertTagDtoToEntity)
            .collect(Collectors.toList());
    adviceEntity.setTags(tags);
    return adviceEntity;
  }

  public List<AdviceDto> convertListAdviceEntityToDto(Principal principal, List<Advice> advices){
    return advices
            .stream()
            .map(entity -> convertAdviceEntityToDto(entity,userConverter.getUserFromPrincipal(principal)))
            .collect(Collectors.toList());
  }

  private String getMediaUrlFromMediaId(Long mediaId) {
    return "localhost:8080" + "/api/media/" + mediaId;
  }
}
