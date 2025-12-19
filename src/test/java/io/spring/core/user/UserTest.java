package io.spring.core.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

  private User user;

  @BeforeEach
  public void setUp() {
    user = new User("test@example.com", "testuser", "password123", "Original bio", "http://image.url/original.jpg");
  }

  @Test
  public void should_create_user_with_all_fields() {
    assertThat(user.getEmail(), is("test@example.com"));
    assertThat(user.getUsername(), is("testuser"));
    assertThat(user.getPassword(), is("password123"));
    assertThat(user.getBio(), is("Original bio"));
    assertThat(user.getImage(), is("http://image.url/original.jpg"));
    assertThat(user.getId(), notNullValue());
  }

  @Test
  public void should_generate_unique_ids_for_different_users() {
    User user1 = new User("user1@example.com", "user1", "pass1", "bio1", "image1");
    User user2 = new User("user2@example.com", "user2", "pass2", "bio2", "image2");

    assertThat(user1.getId(), not(user2.getId()));
  }

  @Test
  public void should_update_email_only() {
    user.update("new@example.com", null, null, null, null);

    assertThat(user.getEmail(), is("new@example.com"));
    assertThat(user.getUsername(), is("testuser"));
    assertThat(user.getPassword(), is("password123"));
    assertThat(user.getBio(), is("Original bio"));
    assertThat(user.getImage(), is("http://image.url/original.jpg"));
  }

  @Test
  public void should_update_username_only() {
    user.update(null, "newusername", null, null, null);

    assertThat(user.getEmail(), is("test@example.com"));
    assertThat(user.getUsername(), is("newusername"));
    assertThat(user.getPassword(), is("password123"));
  }

  @Test
  public void should_update_password_only() {
    user.update(null, null, "newpassword", null, null);

    assertThat(user.getEmail(), is("test@example.com"));
    assertThat(user.getUsername(), is("testuser"));
    assertThat(user.getPassword(), is("newpassword"));
  }

  @Test
  public void should_update_bio_only() {
    user.update(null, null, null, "New bio", null);

    assertThat(user.getBio(), is("New bio"));
    assertThat(user.getImage(), is("http://image.url/original.jpg"));
  }

  @Test
  public void should_update_image_only() {
    user.update(null, null, null, null, "http://image.url/new.jpg");

    assertThat(user.getBio(), is("Original bio"));
    assertThat(user.getImage(), is("http://image.url/new.jpg"));
  }

  @Test
  public void should_update_all_fields() {
    user.update("new@example.com", "newuser", "newpass", "New bio", "http://new.image");

    assertThat(user.getEmail(), is("new@example.com"));
    assertThat(user.getUsername(), is("newuser"));
    assertThat(user.getPassword(), is("newpass"));
    assertThat(user.getBio(), is("New bio"));
    assertThat(user.getImage(), is("http://new.image"));
  }

  @Test
  public void should_not_update_when_values_are_empty() {
    user.update("", "", "", "", "");

    assertThat(user.getEmail(), is("test@example.com"));
    assertThat(user.getUsername(), is("testuser"));
    assertThat(user.getPassword(), is("password123"));
    assertThat(user.getBio(), is("Original bio"));
    assertThat(user.getImage(), is("http://image.url/original.jpg"));
  }

  @Test
  public void should_not_update_when_values_are_null() {
    user.update(null, null, null, null, null);

    assertThat(user.getEmail(), is("test@example.com"));
    assertThat(user.getUsername(), is("testuser"));
    assertThat(user.getPassword(), is("password123"));
    assertThat(user.getBio(), is("Original bio"));
    assertThat(user.getImage(), is("http://image.url/original.jpg"));
  }

  @Test
  public void should_be_equal_when_ids_are_same() {
    assertThat(user.equals(user), is(true));
  }

  @Test
  public void should_not_be_equal_when_ids_are_different() {
    User anotherUser = new User("test@example.com", "testuser", "password123", "bio", "image");
    assertThat(user.equals(anotherUser), is(false));
  }

  @Test
  public void should_have_consistent_hashcode() {
    int hashCode1 = user.hashCode();
    int hashCode2 = user.hashCode();
    assertThat(hashCode1, is(hashCode2));
  }

  @Test
  public void should_preserve_id_when_updating() {
    String originalId = user.getId();
    user.update("new@example.com", "newuser", "newpass", "New bio", "http://new.image");

    assertThat(user.getId(), is(originalId));
  }
}
