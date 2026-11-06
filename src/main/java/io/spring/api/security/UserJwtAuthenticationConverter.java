package io.spring.api.security;

import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import java.util.Collections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;

/** Resolves the JWT subject (user ID) to a {@link User} principal. */
public class UserJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final UserRepository userRepository;

  public UserJwtAuthenticationConverter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    String userId = jwt.getSubject();
    if (userId == null) {
      throw new InvalidBearerTokenException("Token has no subject");
    }
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidBearerTokenException("Unknown user for token subject"));
    return UsernamePasswordAuthenticationToken.authenticated(user, jwt, Collections.emptyList());
  }
}
