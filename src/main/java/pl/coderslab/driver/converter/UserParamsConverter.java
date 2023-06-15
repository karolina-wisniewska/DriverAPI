package pl.coderslab.driver.converter;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.entity.UserParams;
import pl.coderslab.driver.entity.security.User;
import pl.coderslab.driver.model.UserParamsDto;

@Component
public class UserParamsConverter {

  public UserParamsDto convertUserParamsEntityToDto(UserParams userParamsEntity){
    UserParamsDto userParamsDto = new UserParamsDto();
    userParamsDto.setId(userParamsEntity.getId());
    User user = userParamsEntity.getUser();
    userParamsDto.setUserName(user.getUserName());
    userParamsDto.setPoints(userParamsEntity.getPoints());
    return userParamsDto;
  }

}
