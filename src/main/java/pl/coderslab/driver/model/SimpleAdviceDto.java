package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Tag;

import java.util.List;

@Data
public class SimpleAdviceDto {

  private Long id;

  private String name;

  private String description;

  private List<Tag> tags;

}
