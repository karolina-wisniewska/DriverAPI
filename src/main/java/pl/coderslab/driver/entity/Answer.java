package pl.coderslab.driver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "answers")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="varchar(200)")
  private String content;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Answer answer)) return false;
    return Objects.equals(id, answer.id) && Objects.equals(content, answer.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content);
  }
}
