package pl.coderslab.driver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.coderslab.driver.entity.Tag;
import pl.coderslab.driver.service.TagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(TagControllerTestConfig.class)
@WebMvcTest(TagController.class)
@AutoConfigureMockMvc
class TagControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TagService tagService;

  @Autowired
  private ObjectMapper objectMapper;

  // JUnit test for Get All tags REST API
  @Test
  @WithMockUser
  public void givenListOfTags_whenGetAllTags_thenReturnTagsList() throws Exception{
    // given - precondition or setup
    List<Tag> listOfTags = new ArrayList<>();
    listOfTags.add(Tag.builder().name("Tag1").build());
    listOfTags.add(Tag.builder().name("Tag2").build());
    given(tagService.findAll()).willReturn(listOfTags);

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/tags"));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()",
                    is(listOfTags.size())));

  }

  // positive scenario - valid tag id
  // JUnit test for GET tag by id REST API
  @Test
  @WithMockUser
  public void givenTagId_whenGetTagById_thenReturnTagObject() throws Exception{
    // given - precondition or setup
    long tagId = 1L;
    Tag tag = Tag.builder()
            .name("Tag1")
            .build();
    given(tagService.findById(tagId)).willReturn(Optional.of(tag));

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/tags/{tagId}", tagId));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name", is(tag.getName())));
  }

  // negative scenario - invalid tag id
  // JUnit test for GET tag by id REST API
  @Test
  @WithMockUser
  public void givenInvalidTagId_whenGetTagById_thenReturnEmpty() throws Exception {
    // given - precondition or setup
    long tagId = 1L;
    given(tagService.findById(tagId)).willReturn(Optional.empty());

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/tags/{tagId}", tagId));

    // then - verify the output
    response.andExpect(status().isNotFound())
            .andDo(print());
  }

  // positive scenario - valid tag name
  // JUnit test for GET tag by name REST API
  @Test
  @WithMockUser
  public void givenTagName_whenGetTagByName_thenReturnTagObject() throws Exception{
    // given - precondition or setup
    String tagName = "Tag1";
    Tag tag = Tag.builder()
            .name("Tag1")
            .build();
    given(tagService.findByName(tagName)).willReturn(Optional.of(tag));

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/tags?search={tagName}", tagName));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name", is(tag.getName())));
  }

  // negative scenario - invalid tag name
  // JUnit test for GET tag by name REST API
  @Test
  @WithMockUser
  public void givenInvalidTagName_whenGetTagByName_thenReturnEmpty() throws Exception {
    // given - precondition or setup
    String tagName = "Tag1";

    given(tagService.findByName(tagName)).willReturn(Optional.empty());

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/tags?search={tagName}", tagName));

    // then - verify the output
    response.andExpect(status().isNotFound())
            .andDo(print());
  }

  @Test
  @WithMockUser
  public void givenTagObject_whenCreateTag_thenReturnSavedTag() throws Exception{

    // given - precondition or setup
    Tag tag = Tag.builder()
            .name("Tag1")
            .build();
    given(tagService.save(any(Tag.class)))
            .willAnswer((invocation)-> invocation.getArgument(0));

    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(post("/api/tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tag)));

    // then - verify the result or output using assert statements
    response.andDo(print()).
            andExpect(status().isCreated())
            .andExpect(jsonPath("$.name",
                    is(tag.getName())));
  }

  @Test
  @WithMockUser
  public void givenUpdatedTag_whenUpdateTag_thenReturnUpdateTagObject() throws Exception{
    // given - precondition or setup
    long tagId = 1L;
    Tag savedTag = Tag.builder()
            .name("Tag1")
            .build();

    Tag updatedTag = Tag.builder()
            .name("Updated Tag1")
            .build();
    given(tagService.findById(tagId)).willReturn(Optional.of(savedTag));
    given(tagService.update(any(Tag.class)))
            .willAnswer((invocation)-> invocation.getArgument(0));

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(put("/api/tags/{tagId}", tagId).with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedTag)));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name", is(updatedTag.getName())));
  }

  // JUnit test for delete tag REST API
  @Test
  @WithMockUser
  public void givenTagId_whenDeleteTag_thenReturn200() throws Exception{
    // given - precondition or setup
    long tagId = 1L;
    willDoNothing().given(tagService).deleteById(tagId);

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(delete("/api/tags/{tagId}", tagId).with(csrf()));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print());
  }
}