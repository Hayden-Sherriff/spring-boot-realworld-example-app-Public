package io.spring;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.spring.application.DateTimes;
import java.io.IOException;
import java.time.OffsetDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonCustomizations {

  @Bean
  public Module realWorldModules() {
    return new RealWorldModules();
  }

  public static class RealWorldModules extends SimpleModule {
    public RealWorldModules() {
      addSerializer(OffsetDateTime.class, new DateTimeSerializer());
    }
  }

  public static class DateTimeSerializer extends StdSerializer<OffsetDateTime> {

    protected DateTimeSerializer() {
      super(OffsetDateTime.class);
    }

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
      if (value == null) {
        gen.writeNull();
      } else {
        gen.writeString(DateTimes.format(value));
      }
    }
  }
}
