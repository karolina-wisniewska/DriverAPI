package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.driver.entity.Advice;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.model.AdviceDto;
import pl.coderslab.driver.model.UserParamsDto;
import pl.coderslab.driver.service.AdviceService;
import pl.coderslab.driver.service.UserParamsService;
import pl.coderslab.driver.service.security.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class UserParamsController {

  private final AdviceService adviceService;
  private final UserService userService;
  private final UserParamsService userParamsService;

  @GetMapping("/to-do")
  @ResponseStatus(HttpStatus.OK)
  public List<AdviceDto> getAdvicesToDo(Principal principal) {
    String username = principal.getName();
    User user = userService.findUserByUserName(username);
    List<Advice> passedAdvices = adviceService.findAllByUser(user);
    return adviceService.findAdvicesToDo(passedAdvices)
            .stream()
            .map(this::convertAdviceEntityToAdviceDto)
            .collect(Collectors.toList());
  }

  @GetMapping("/details")
  @ResponseStatus(HttpStatus.OK)
  public UserParamsDto getUserParams(Principal principal){
    String username = principal.getName();
    User user = userService.findUserByUserName(username);
    return userParamsService.findByUser(user)
            .map(this::convertUserParamsEntityToDto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
  }

  private UserParamsDto convertUserParamsEntityToDto(UserParams userParamsEntity){
    UserParamsDto userParamsDto = new UserParamsDto();
    userParamsDto.setId(userParamsEntity.getId());
    User user = userParamsEntity.getUser();
    userParamsDto.setUserName(user.getUserName());
    userParamsDto.setPoints(userParamsEntity.getPoints());
    return userParamsDto;
  }

  private AdviceDto convertAdviceEntityToAdviceDto(Advice adviceEntity){
    AdviceDto adviceDto = new AdviceDto();
    adviceDto.setId(adviceEntity.getId());
    adviceDto.setName(adviceEntity.getName());
    adviceDto.setDescription(adviceEntity.getDescription());
    adviceDto.setTags(adviceEntity.getTags());
    adviceDto.setShares(adviceEntity.getShares());
    adviceDto.setLikes(adviceEntity.getLikes());
    return adviceDto;
  }
}
