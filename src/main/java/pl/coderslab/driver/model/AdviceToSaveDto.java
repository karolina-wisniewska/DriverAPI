package pl.coderslab.driver.model;

import lombok.Data;

import java.util.List;

@Data
public class AdviceToSaveDto {

  private Long id;
  private String name;
  private Long mediaId;
  private String description;
  private List<TagDto> tags;
}
