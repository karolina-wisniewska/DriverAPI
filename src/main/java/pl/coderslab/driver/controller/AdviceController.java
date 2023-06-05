package pl.coderslab.driver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.driver.entity.Advice;

@RestController
@RequestMapping("/api")
public class AdviceResource {

  @GetMapping("/helloAdvice")
  @ResponseStatus(HttpStatus.OK)
  public Advice helloAdvice(){
    return new Advice(1L, "First Advice");
  }
}
