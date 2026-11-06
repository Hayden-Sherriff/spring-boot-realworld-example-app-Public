package io.spring.api.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {
  public static final MacAlgorithm ALGORITHM = MacAlgorithm.HS512;

  private static final int MIN_SECRET_BYTES = 64;

  @Bean
  public JwtEncoder jwtEncoder(@Value("${jwt.secret}") String secret) {
    return new NimbusJwtEncoder(new ImmutableSecret<>(signingKey(secret)));
  }

  @Bean
  public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secret) {
    return NimbusJwtDecoder.withSecretKey(signingKey(secret)).macAlgorithm(ALGORITHM).build();
  }

  static SecretKey signingKey(String secret) {
    byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
    if (bytes.length < MIN_SECRET_BYTES) {
      throw new IllegalStateException(
          "jwt.secret must be at least " + MIN_SECRET_BYTES + " bytes for " + ALGORITHM);
    }
    return new SecretKeySpec(bytes, "HmacSHA512");
  }
}
