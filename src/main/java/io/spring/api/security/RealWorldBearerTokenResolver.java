package io.spring.api.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

/**
 * Resolves the JWT from the Authorization header. The RealWorld spec uses the {@code Token} scheme;
 * the standard {@code Bearer} scheme is accepted as well.
 */
public class RealWorldBearerTokenResolver implements BearerTokenResolver {
  private static final List<String> SCHEMES = List.of("Token", "Bearer");

  @Override
  public String resolve(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null) {
      return null;
    }
    for (String scheme : SCHEMES) {
      int prefixLength = scheme.length() + 1;
      if (header.length() > prefixLength
          && header.regionMatches(true, 0, scheme + " ", 0, prefixLength)) {
        String token = header.substring(prefixLength).trim();
        return token.isEmpty() ? null : token;
      }
    }
    return null;
  }
}
