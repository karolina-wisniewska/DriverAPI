package pl.coderslab.driver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "advices")
public class Advice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="varchar(50)")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "media_content_id", insertable = false, updatable = false)
  private MediaContent mediaContent;

  @Column(name = "media_content_id")
  private Long mediaId;

  @Column(columnDefinition="text")
  private String description;

  @ManyToMany
  @JoinTable(name = "advices_tags",
          joinColumns = @JoinColumn(name = "advice_id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<Tag> tags;

  private Long likes;
  private Long shares;

}
