package io.spring.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import io.spring.application.data.UserData;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import io.spring.infrastructure.mybatis.readservice.UserReadService;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

abstract class TestWithCurrentUser {
  @MockitoBean protected UserRepository userRepository;

  @MockitoBean protected UserReadService userReadService;

  @MockitoBean protected JwtDecoder jwtDecoder;

  protected User user;
  protected UserData userData;
  protected String token;
  protected String email;
  protected String username;
  protected String defaultAvatar;

  @MockitoBean protected JwtService jwtService;

  protected void userFixture() {
    email = "john@jacob.com";
    username = "johnjacob";
    defaultAvatar = "https://static.productionready.io/images/smiley-cyrus.jpg";

    user = new User(email, username, "123", "", defaultAvatar);
    when(userRepository.findByUsername(eq(username))).thenReturn(Optional.of(user));
    when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

    userData = new UserData(user.getId(), email, username, "", defaultAvatar);
    when(userReadService.findById(eq(user.getId()))).thenReturn(userData);

    when(jwtDecoder.decode(anyString())).thenThrow(new BadJwtException("invalid token"));
    token = tokenFor(user);
  }

  protected String tokenFor(User subject) {
    String tokenValue = "token-" + subject.getId();
    Jwt jwt =
        Jwt.withTokenValue(tokenValue)
            .header("alg", "HS512")
            .subject(subject.getId())
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();
    doReturn(jwt).when(jwtDecoder).decode(eq(tokenValue));
    return tokenValue;
  }

  @BeforeEach
  public void setUp() throws Exception {
    userFixture();
  }
}
