package io.spring.application.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class ProfileDataTest {

  @Test
  public void should_create_profile_data_with_all_fields() {
    ProfileData profileData = new ProfileData("user-id", "username", "bio", "image-url", true);

    assertThat(profileData.getId(), is("user-id"));
    assertThat(profileData.getUsername(), is("username"));
    assertThat(profileData.getBio(), is("bio"));
    assertThat(profileData.getImage(), is("image-url"));
    assertThat(profileData.isFollowing(), is(true));
  }

  @Test
  public void should_create_profile_data_with_no_args_constructor() {
    ProfileData profileData = new ProfileData();
    assertThat(profileData.isFollowing(), is(false));
  }

  @Test
  public void should_set_and_get_following() {
    ProfileData profileData = new ProfileData();
    profileData.setFollowing(true);
    assertThat(profileData.isFollowing(), is(true));
  }

  @Test
  public void should_set_and_get_username() {
    ProfileData profileData = new ProfileData();
    profileData.setUsername("newuser");
    assertThat(profileData.getUsername(), is("newuser"));
  }

  @Test
  public void should_set_and_get_bio() {
    ProfileData profileData = new ProfileData();
    profileData.setBio("new bio");
    assertThat(profileData.getBio(), is("new bio"));
  }

  @Test
  public void should_set_and_get_image() {
    ProfileData profileData = new ProfileData();
    profileData.setImage("new-image-url");
    assertThat(profileData.getImage(), is("new-image-url"));
  }

  @Test
  public void should_be_equal_when_all_fields_match() {
    ProfileData profile1 = new ProfileData("id", "user", "bio", "image", true);
    ProfileData profile2 = new ProfileData("id", "user", "bio", "image", true);

    assertThat(profile1, is(profile2));
  }

  @Test
  public void should_have_same_hashcode_when_equal() {
    ProfileData profile1 = new ProfileData("id", "user", "bio", "image", true);
    ProfileData profile2 = new ProfileData("id", "user", "bio", "image", true);

    assertThat(profile1.hashCode(), is(profile2.hashCode()));
  }
}
