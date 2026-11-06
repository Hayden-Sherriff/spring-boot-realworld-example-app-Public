package io.spring.infrastructure.service;

import io.spring.api.security.JwtConfig;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityJwtService implements JwtService {
  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;
  private final Duration sessionTime;

  public SpringSecurityJwtService(
      JwtEncoder jwtEncoder,
      JwtDecoder jwtDecoder,
      @Value("${jwt.sessionTime}") long sessionTimeSeconds) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
    this.sessionTime = Duration.ofSeconds(sessionTimeSeconds);
  }

  @Override
  public String toToken(User user) {
    Instant now = Instant.now();
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .subject(user.getId())
            .issuedAt(now)
            .expiresAt(now.plus(sessionTime))
            .build();
    JwsHeader header = JwsHeader.with(JwtConfig.ALGORITHM).build();
    return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
  }

  @Override
  public Optional<String> getSubFromToken(String token) {
    try {
      return Optional.ofNullable(jwtDecoder.decode(token).getSubject());
    } catch (JwtException e) {
      return Optional.empty();
    }
  }
}
