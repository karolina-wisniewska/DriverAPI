package pl.coderslab.driver.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "questions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="varchar(200)")
  private String content;

  @OneToMany
  @JoinColumn(name = "question_id")
  private List<Answer> answers;

  @ManyToOne(fetch = FetchType.LAZY)
  private Answer correctAnswer;
}
