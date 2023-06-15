package pl.coderslab.driver.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.Answer;
import pl.coderslab.driver.model.AnswerDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnswerConverter {

  public AnswerDto convertAnswerEntityToDto(Answer answerEntity){
    AnswerDto answerDto = new AnswerDto();
    answerDto.setId(answerEntity.getId());
    answerDto.setContent(answerEntity.getContent());
    return answerDto;
  }

  public Answer convertAnswerDtoToEntity(AnswerDto answerDto){
    Answer answer = new Answer();
    answer.setId(answerDto.getId());
    answer.setContent(answerDto.getContent());
    return answer;
  }

  public List<AnswerDto> convertListAnswerEntityToDto(List<Answer> answers){
    return answers
            .stream()
            .map(this::convertAnswerEntityToDto)
            .collect(Collectors.toList());
  }

  public List<Answer> convertListAnswerDtoToEntity(List<AnswerDto> answersDto){
    return answersDto
            .stream()
            .map(this::convertAnswerDtoToEntity)
            .collect(Collectors.toList());
  }

}
