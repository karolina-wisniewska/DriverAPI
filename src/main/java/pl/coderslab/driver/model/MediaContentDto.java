package pl.coderslab.driver.model;

import lombok.Data;

@Data
public class MediaContentDto {

  private Long id;

  private String name;

  private String type;

  private byte[] fullContent;
}
