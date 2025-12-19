package io.spring.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.spring.application.CursorPager.Direction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

public class CursorPagerTest {

  @Test
  public void should_set_next_true_when_direction_is_next_and_has_extra() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, true);

    assertThat(pager.hasNext(), is(true));
    assertThat(pager.hasPrevious(), is(false));
  }

  @Test
  public void should_set_next_false_when_direction_is_next_and_no_extra() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.hasNext(), is(false));
    assertThat(pager.hasPrevious(), is(false));
  }

  @Test
  public void should_set_previous_true_when_direction_is_prev_and_has_extra() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.PREV, true);

    assertThat(pager.hasNext(), is(false));
    assertThat(pager.hasPrevious(), is(true));
  }

  @Test
  public void should_set_previous_false_when_direction_is_prev_and_no_extra() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.PREV, false);

    assertThat(pager.hasNext(), is(false));
    assertThat(pager.hasPrevious(), is(false));
  }

  @Test
  public void should_return_start_cursor_from_first_element() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.getStartCursor().toString(), is(data.get(0).getCursor().toString()));
  }

  @Test
  public void should_return_end_cursor_from_last_element() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.getEndCursor().toString(), is(data.get(2).getCursor().toString()));
  }

  @Test
  public void should_return_null_start_cursor_when_data_is_empty() {
    List<TestNode> data = new ArrayList<>();
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.getStartCursor(), nullValue());
  }

  @Test
  public void should_return_null_end_cursor_when_data_is_empty() {
    List<TestNode> data = new ArrayList<>();
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.getEndCursor(), nullValue());
  }

  @Test
  public void should_return_data() {
    List<TestNode> data = createTestNodes(3);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.getData(), is(data));
  }

  @Test
  public void should_handle_single_element_list() {
    List<TestNode> data = createTestNodes(1);
    CursorPager<TestNode> pager = new CursorPager<>(data, Direction.NEXT, false);

    assertThat(pager.getStartCursor().toString(), is(pager.getEndCursor().toString()));
  }

  private List<TestNode> createTestNodes(int count) {
    List<TestNode> nodes = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      nodes.add(new TestNode(new DateTime().plusMinutes(i)));
    }
    return nodes;
  }

  private static class TestNode implements Node {
    private final DateTime dateTime;

    TestNode(DateTime dateTime) {
      this.dateTime = dateTime;
    }

    @Override
    public PageCursor getCursor() {
      return new DateTimeCursor(dateTime);
    }
  }
}
