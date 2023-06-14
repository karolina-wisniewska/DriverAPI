package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Question;

import java.util.List;

@Data
public class TrainingDto {

  private Long id;

  private Long adviceId;

  private List<Question> questions;
}
