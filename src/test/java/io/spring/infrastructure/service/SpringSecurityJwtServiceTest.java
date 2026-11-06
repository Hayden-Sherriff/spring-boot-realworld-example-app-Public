package io.spring.infrastructure.service;

import io.spring.api.security.JwtConfig;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpringSecurityJwtServiceTest {

  private static final String SECRET =
      "123123123123123123123123123123123123123123123123123123123123123123123123";

  private JwtService jwtService;

  @BeforeEach
  public void setUp() {
    JwtConfig jwtConfig = new JwtConfig();
    jwtService =
        new SpringSecurityJwtService(
            jwtConfig.jwtEncoder(SECRET), jwtConfig.jwtDecoder(SECRET), 3600);
  }

  @Test
  public void should_generate_and_parse_token() {
    User user = new User("email@email.com", "username", "123", "", "");
    String token = jwtService.toToken(user);
    Assertions.assertNotNull(token);
    Optional<String> optional = jwtService.getSubFromToken(token);
    Assertions.assertTrue(optional.isPresent());
    Assertions.assertEquals(optional.get(), user.getId());
  }

  @Test
  public void should_get_null_with_wrong_jwt() {
    Optional<String> optional = jwtService.getSubFromToken("123");
    Assertions.assertFalse(optional.isPresent());
  }

  @Test
  public void should_get_null_with_expired_jwt() {
    String token =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhaXNlbnNpeSIsImV4cCI6MTUwMjE2MTIwNH0.SJB-U60WzxLYNomqLo4G3v3LzFxJKuVrIud8D8Lz3-mgpo9pN1i7C8ikU_jQPJGm8HsC1CquGMI-rSuM7j6LDA";
    Assertions.assertFalse(jwtService.getSubFromToken(token).isPresent());
  }

  @Test
  public void should_reject_token_signed_with_another_key() {
    JwtConfig jwtConfig = new JwtConfig();
    String otherSecret = SECRET.replace('1', '9');
    JwtService other =
        new SpringSecurityJwtService(
            jwtConfig.jwtEncoder(otherSecret), jwtConfig.jwtDecoder(otherSecret), 3600);
    String token = other.toToken(new User("email@email.com", "username", "123", "", ""));
    Assertions.assertFalse(jwtService.getSubFromToken(token).isPresent());
  }

  @Test
  public void should_reject_short_secret() {
    JwtConfig jwtConfig = new JwtConfig();
    Assertions.assertThrows(IllegalStateException.class, () -> jwtConfig.jwtEncoder("short"));
  }
}
