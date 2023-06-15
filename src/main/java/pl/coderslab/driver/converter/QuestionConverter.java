package pl.coderslab.driver.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.Question;
import pl.coderslab.driver.model.QuestionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionConverter {

  private final AnswerConverter answerConverter;
  public QuestionDto convertQuestionEntityToDto(Question questionEntity){
    QuestionDto questionDto = new QuestionDto();
    questionDto.setId(questionEntity.getId());
    questionDto.setContent(questionEntity.getContent());
    questionDto.setAnswers(answerConverter.convertListAnswerEntityToDto(questionEntity.getAnswers()));
    questionDto.setCorrectAnswer(answerConverter.convertAnswerEntityToDto(questionEntity.getCorrectAnswer()));
    return questionDto;
  }

  public Question convertQuestionDtoToEntity(QuestionDto questionDto){
    Question question = new Question();
    question.setId(questionDto.getId());
    question.setContent(questionDto.getContent());
    question.setAnswers(answerConverter.convertListAnswerDtoToEntity(questionDto.getAnswers()));
    question.setCorrectAnswer(answerConverter.convertAnswerDtoToEntity(questionDto.getCorrectAnswer()));
    return question;
  }

  public List<QuestionDto> convertListQuestionEntityToDto(List<Question> questions){
    return questions
            .stream()
            .map(this::convertQuestionEntityToDto)
            .collect(Collectors.toList());
  }

  public List<Question> convertListQuestionDtoToEntity(List<QuestionDto> questionsDto){
    return questionsDto
            .stream()
            .map(this::convertQuestionDtoToEntity)
            .collect(Collectors.toList());
  }

}
