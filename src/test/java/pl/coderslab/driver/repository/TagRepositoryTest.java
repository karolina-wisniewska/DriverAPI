package pl.coderslab.driver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.coderslab.driver.config.RsaKeyProperties;
import pl.coderslab.driver.entity.Tag;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  TagRepository repository;

  @MockBean
  private RsaKeyProperties rsaKeyProperties;

  @Test
  public void should_find_no_tags_if_repository_is_empty() {
    Iterable tags = repository.findAll();

    assertThat(tags).isEmpty();
  }

  @Test
  public void should_store_a_tag() {
    Tag tag = repository.save(Tag.builder().name("Tag").build());

    assertThat(tag).hasFieldOrPropertyWithValue("name", "Tag");
  }

  @Test
  public void should_find_all_tags() {
    Tag tag1 = Tag.builder().name("Tag1").build();
    entityManager.persist(tag1);

    Tag tag2 = Tag.builder().name("Tag2").build();
    entityManager.persist(tag2);

    Tag tag3 = Tag.builder().name("Tag3").build();
    entityManager.persist(tag3);

    Iterable tags = repository.findAll();

    assertThat(tags).hasSize(3).contains(tag1, tag2, tag3);
  }

  @Test
  public void should_find_tag_by_id() {
    Tag tag1 = Tag.builder().name("Tag1").build();
    entityManager.persist(tag1);

    Tag tag2 = Tag.builder().name("Tag2").build();
    entityManager.persist(tag2);

    Tag foundTag = repository.findById(tag1.getId()).get();

    assertThat(foundTag).isEqualTo(tag1);
  }

  @Test
  public void should_find_tag_by_name() {
    Tag tag1 = Tag.builder().name("Tag1").build();
    entityManager.persist(tag1);

    Tag tag2 = Tag.builder().name("Tag2").build();
    entityManager.persist(tag2);

    Tag tag3 = Tag.builder().name("Tag3").build();
    entityManager.persist(tag3);

    Tag foundTag = repository.findByName(tag2.getName()).get();

    assertThat(foundTag).isEqualTo(tag2);
  }

  @Test
  public void should_update_tag_by_id() {
    Tag tag1 = Tag.builder().name("Tag1").build();
    entityManager.persist(tag1);

    Tag tag2 = Tag.builder().name("Tag2").build();
    entityManager.persist(tag2);

    Tag updatedTag = Tag.builder().name("UpdatedTag2").build();

    Tag tag = repository.findById(tag2.getId()).get();
    tag.setName(updatedTag.getName());
    repository.save(tag);

    Tag checkTag = repository.findById(tag2.getId()).get();

    assertThat(checkTag.getId()).isEqualTo(tag2.getId());
    assertThat(checkTag.getName()).isEqualTo(updatedTag.getName());
  }

  @Test
  public void should_delete_tag_by_id() {
    Tag tag1 = Tag.builder().name("Tag1").build();
    entityManager.persist(tag1);

    Tag tag2 = Tag.builder().name("Tag2").build();
    entityManager.persist(tag2);

    Tag tag3 = Tag.builder().name("Tag3").build();
    entityManager.persist(tag3);

    repository.deleteById(tag2.getId());

    Iterable tags = repository.findAll();

    assertThat(tags).hasSize(2).contains(tag1, tag3);
  }

}