package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Tag;

import java.util.List;

@Data
public class FullAdviceDto {

  private Long id;

  private String name;

  private String fullContentUrl;

  private String description;

  private List<Tag> tags;

  private Long likes;
  private Long shares;
}
