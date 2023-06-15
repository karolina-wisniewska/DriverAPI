package pl.coderslab.driver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AnswerDto {

  private Long id;

  private String content;
}
