package pl.coderslab.driver.model;

import lombok.Data;
import pl.coderslab.driver.entity.Answer;

import java.util.List;

@Data
public class QuestionDto {

  private Long id;

  private String content;

  private List<Answer> answers;

  private Answer correctAnswer;
}
