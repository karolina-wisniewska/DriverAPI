package pl.coderslab.driver.service;

import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

  @Mock
  private TagRepository tagRepository;
  @InjectMocks
  private TagService tagService;
  private Tag tag;

  @BeforeEach
  public void setup(){
    tag = Tag.builder()
            .id(1L)
            .name("tag1")
            .build();
  }

  @DisplayName("JUnit test for findAll method")
  @Test
  public void givenTagsList_whenFindAllTags_thenReturnTagsList(){
    // given - precondition or setup

    Tag tag1 = Tag.builder()
            .id(2L)
            .name("tag2")
            .build();

    given(tagRepository.findAll()).willReturn(List.of(tag,tag1));

    // when -  action or the behaviour that we are going test
    List<Tag> tagList = tagService.findAll();

    // then - verify the output
    assertThat(tagList).isNotNull();
    assertThat(tagList.size()).isEqualTo(2);
  }

  @DisplayName("JUnit test for findById method")
  @Test
  public void givenTagId_whenGetTagById_thenReturnTagObject(){
    // given
    given(tagRepository.findById(1L)).willReturn(Optional.of(tag));

    // when
    Tag savedTag = tagService.findById(tag.getId()).get();

    // then
    assertThat(savedTag).isNotNull();

  }

  @DisplayName("JUnit test for findByName method")
  @Test
  public void givenTagName_whenGetTagByName_thenReturnTagObject(){
    // given
    given(tagRepository.findByName("tag1")).willReturn(Optional.of(tag));

    // when
    Tag savedTag = tagService.findByName(tag.getName()).get();

    // then
    assertThat(savedTag).isNotNull();

  }

  @DisplayName("JUnit test for save method")
  @Test
  public void givenTagObject_whenSaveTag_thenReturnTagObject(){
    // given - precondition or setup
    given(tagRepository.findByName(tag.getName()))
            .willReturn(Optional.empty());

    given(tagRepository.save(tag)).willReturn(tag);

    // when -  action or the behaviour that we are going test
    Tag savedTag = tagService.save(tag);

    System.out.println(savedTag);
    // then - verify the output
    assertThat(savedTag).isNotNull();
  }
  
  @DisplayName("JUnit test for save method which throws exception")
  @Test
  public void givenExistingEmail_whenSaveTag_thenThrowsException(){
    // given - precondition or setup
    given(tagRepository.findByName(tag.getName()))
            .willReturn(Optional.of(tag));

    // when -  action or the behaviour that we are going test
    assertThrows(EntityExistsException.class, () -> {
      tagService.save(tag);
    });

    // then
    verify(tagRepository, never()).save(any(Tag.class));
  }

  @DisplayName("JUnit test for deleteById method")
  @Test
  public void givenTagId_whenDelete_thenNothing(){
    // given - precondition or setup
    long tagId = 1L;

    willDoNothing().given(tagRepository).deleteById(tagId);

    // when -  action or the behaviour that we are going test
    tagService.deleteById(tagId);

    // then - verify the output
    verify(tagRepository, times(1)).deleteById(tagId);
  }
}