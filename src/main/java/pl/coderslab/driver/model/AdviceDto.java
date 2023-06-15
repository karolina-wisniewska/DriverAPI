package pl.coderslab.driver.model;

import lombok.Data;

import java.util.List;

@Data
public class AdviceDto {

  private Long id;
  private String name;
  private String mediaUrl;
  private String description;
  private List<TagDto> tags;
  private Long likes;
  private Long shares;
  private boolean isPassed;
}
