package io.spring.application.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class UserDataTest {

  @Test
  public void should_create_user_data_with_all_fields() {
    UserData userData = new UserData("user-id", "user@example.com", "username", "bio", "image-url");

    assertThat(userData.getId(), is("user-id"));
    assertThat(userData.getEmail(), is("user@example.com"));
    assertThat(userData.getUsername(), is("username"));
    assertThat(userData.getBio(), is("bio"));
    assertThat(userData.getImage(), is("image-url"));
  }

  @Test
  public void should_create_user_data_with_no_args_constructor() {
    UserData userData = new UserData();
    assertThat(userData.getId(), is((String) null));
  }

  @Test
  public void should_set_and_get_email() {
    UserData userData = new UserData();
    userData.setEmail("new@example.com");
    assertThat(userData.getEmail(), is("new@example.com"));
  }

  @Test
  public void should_set_and_get_username() {
    UserData userData = new UserData();
    userData.setUsername("newuser");
    assertThat(userData.getUsername(), is("newuser"));
  }

  @Test
  public void should_set_and_get_bio() {
    UserData userData = new UserData();
    userData.setBio("new bio");
    assertThat(userData.getBio(), is("new bio"));
  }

  @Test
  public void should_set_and_get_image() {
    UserData userData = new UserData();
    userData.setImage("new-image-url");
    assertThat(userData.getImage(), is("new-image-url"));
  }

  @Test
  public void should_be_equal_when_all_fields_match() {
    UserData user1 = new UserData("id", "email", "user", "bio", "image");
    UserData user2 = new UserData("id", "email", "user", "bio", "image");

    assertThat(user1, is(user2));
  }

  @Test
  public void should_have_same_hashcode_when_equal() {
    UserData user1 = new UserData("id", "email", "user", "bio", "image");
    UserData user2 = new UserData("id", "email", "user", "bio", "image");

    assertThat(user1.hashCode(), is(user2.hashCode()));
  }
}
