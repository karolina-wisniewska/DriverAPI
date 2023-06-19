package pl.coderslab.driver.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.coderslab.driver.converter.TagConverter;


@TestConfiguration
class TagControllerTestConfig {

  @Bean
  public TagConverter tagConverter() {
    return new TagConverter();
  }
}