package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.converter.AdviceConverter;
import pl.coderslab.driver.converter.UserConverter;
import pl.coderslab.driver.converter.UserParamsConverter;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.model.AdviceDto;
import pl.coderslab.driver.model.UserParamsDto;
import pl.coderslab.driver.service.AdviceService;
import pl.coderslab.driver.service.UserParamsService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class UserParamsController {

  private final AdviceService adviceService;
  private final UserParamsService userParamsService;
  private final AdviceConverter adviceConverter;
  private final UserConverter userConverter;
  private final UserParamsConverter userParamsConverter;

  @GetMapping("/to-do")
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> getAdvicesToDo(Principal principal) {
    List<Advice> passedAdvices = adviceService.findAllByUser(userConverter.getUserFromPrincipal(principal));
    return adviceConverter.convertListAdviceEntityToDto(principal, adviceService.findAdvicesToDo(passedAdvices));
  }

  @GetMapping("/details")
  @ResponseStatus(HttpStatus.OK)
  public UserParamsDto getUserParams(Principal principal){
    return userParamsService.findByUser(userConverter.getUserFromPrincipal(principal))
            .map(userParamsConverter::convertUserParamsEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }
}
