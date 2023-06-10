package pl.coderslab.driver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.
            authorizeHttpRequests(authorization -> authorization
                    .requestMatchers("/**", "api/**", "/swagger-ui/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
            )
            .formLogin(form -> form
                    .usernameParameter("user_name")
                    .passwordParameter("password")
            );

    return httpSecurity.build();
  }

}
