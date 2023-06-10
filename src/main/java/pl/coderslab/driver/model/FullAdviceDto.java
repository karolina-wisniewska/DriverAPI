package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Tag;

import java.sql.Blob;
import java.util.List;

@Data
public class FullAdviceDto {

  private Long id;

  private String name;

  private Blob fullContent;

  private String description;

  private List<Tag> tags;

  private Long likes;
  private Long shares;
}
