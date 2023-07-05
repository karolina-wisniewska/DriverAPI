package pl.coderslab.driver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "media")
public class MediaConfigProperties {

  private String address;
  private int port;
}