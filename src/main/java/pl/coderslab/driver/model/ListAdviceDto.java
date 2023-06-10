package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Tag;

import java.sql.Blob;
import java.util.List;

@Data
public class ListAdviceDto {

  private Long id;

  private String name;

  private Blob cover;

  private String description;

  private List<Tag> tags;

}
