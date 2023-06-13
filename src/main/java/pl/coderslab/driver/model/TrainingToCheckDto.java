package pl.coderslab.driver.model;

import lombok.Data;

import java.util.Map;

@Data
public class TrainingToCheckDto {

  private Map<Long, Long> questionAnswerMap;

}
