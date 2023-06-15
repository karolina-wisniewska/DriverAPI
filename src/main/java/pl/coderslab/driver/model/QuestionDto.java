package pl.coderslab.driver.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {

  private Long id;

  private String content;

  private List<AnswerDto> answers;

  private AnswerDto correctAnswer;
}
