package pl.coderslab.driver.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.model.TagDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagConverter {

  public TagDto convertTagEntityToDto(Tag tagEntity){
    TagDto tagDto = new TagDto();
    tagDto.setId(tagEntity.getId());
    tagDto.setName(tagEntity.getName());
    return tagDto;
  }

  public Tag convertTagDtoToEntity(TagDto tagDto){
    Tag tag = new Tag();
    tag.setId(tagDto.getId());
    tag.setName(tagDto.getName());
    return tag;
  }

  public List<TagDto> convertListTagEntityToDto(List<Tag> tags){
    return tags
            .stream()
            .map(this::convertTagEntityToDto)
            .collect(Collectors.toList());
  }

  public List<Tag> convertListTagDtoToEntity(List<TagDto> tagsDto){
    return tagsDto
            .stream()
            .map(this::convertTagDtoToEntity)
            .collect(Collectors.toList());
  }

}
