package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Tag;

import java.util.List;

@Data
public class AdviceDto {

  private Long id;
  private String name;
  private Long mediaId;
  private String mediaUrl;
  private String description;
  private List<Tag> tags;
  private Long likes;
  private Long shares;
}
