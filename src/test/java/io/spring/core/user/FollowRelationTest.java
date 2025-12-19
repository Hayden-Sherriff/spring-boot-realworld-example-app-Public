package io.spring.core.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class FollowRelationTest {

  @Test
  public void should_create_follow_relation() {
    FollowRelation followRelation = new FollowRelation("user1", "user2");

    assertThat(followRelation.getUserId(), is("user1"));
    assertThat(followRelation.getTargetId(), is("user2"));
  }

  @Test
  public void should_be_equal_when_user_and_target_are_same() {
    FollowRelation relation1 = new FollowRelation("user1", "user2");
    FollowRelation relation2 = new FollowRelation("user1", "user2");

    assertThat(relation1, is(relation2));
  }

  @Test
  public void should_not_be_equal_when_users_are_different() {
    FollowRelation relation1 = new FollowRelation("user1", "user2");
    FollowRelation relation2 = new FollowRelation("user3", "user2");

    assertThat(relation1, not(relation2));
  }

  @Test
  public void should_not_be_equal_when_targets_are_different() {
    FollowRelation relation1 = new FollowRelation("user1", "user2");
    FollowRelation relation2 = new FollowRelation("user1", "user3");

    assertThat(relation1, not(relation2));
  }

  @Test
  public void should_have_same_hashcode_when_equal() {
    FollowRelation relation1 = new FollowRelation("user1", "user2");
    FollowRelation relation2 = new FollowRelation("user1", "user2");

    assertThat(relation1.hashCode(), is(relation2.hashCode()));
  }

  @Test
  public void should_allow_self_follow() {
    FollowRelation selfFollow = new FollowRelation("user1", "user1");

    assertThat(selfFollow.getUserId(), is("user1"));
    assertThat(selfFollow.getTargetId(), is("user1"));
  }

  @Test
  public void should_set_user_id() {
    FollowRelation relation = new FollowRelation("user1", "user2");
    relation.setUserId("user3");

    assertThat(relation.getUserId(), is("user3"));
  }

  @Test
  public void should_set_target_id() {
    FollowRelation relation = new FollowRelation("user1", "user2");
    relation.setTargetId("user3");

    assertThat(relation.getTargetId(), is("user3"));
  }
}
