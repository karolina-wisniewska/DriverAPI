package pl.coderslab.driver.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.pojo.AuthenticationRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JwtEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  public String generateToken(AuthenticationRequest request) {

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUserName(),
                    request.getPassword()
            )
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());

    Instant now = Instant.now();
    String scope = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(request.getUserName())
            .claim("scope", scope)
            .build();
    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
