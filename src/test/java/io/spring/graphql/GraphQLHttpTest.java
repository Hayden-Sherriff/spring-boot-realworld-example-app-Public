package io.spring.graphql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GraphQLHttpTest {

  @Autowired private MockMvc mvc;

  @Test
  public void should_execute_anonymous_query_over_http() throws Exception {
    mvc.perform(
            post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"query\":\"{ articles(first: 5) { edges { node { slug } } } }\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").doesNotExist())
        .andExpect(jsonPath("$.data.articles.edges").isArray());
  }

  @Test
  public void should_serve_public_query_anonymously_with_invalid_token() throws Exception {
    mvc.perform(
            post("/graphql")
                .header("Authorization", "Token invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"query\":\"{ articles(first: 5) { edges { node { slug } } } }\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.articles.edges").isArray());
  }

  @Test
  public void should_serve_public_rest_endpoint_anonymously_with_invalid_token() throws Exception {
    mvc.perform(get("/tags").header("Authorization", "Token invalid")).andExpect(status().isOk());
  }

  @Test
  public void should_reject_invalid_token_on_protected_rest_endpoint() throws Exception {
    mvc.perform(get("/user").header("Authorization", "Token invalid"))
        .andExpect(status().isUnauthorized());
  }
}
