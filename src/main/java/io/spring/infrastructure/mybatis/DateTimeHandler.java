package io.spring.infrastructure.mybatis;

import io.spring.application.DateTimes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.TimeZone;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(OffsetDateTime.class)
public class DateTimeHandler implements TypeHandler<OffsetDateTime> {

  private static final Calendar UTC_CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

  @Override
  public void setParameter(PreparedStatement ps, int i, OffsetDateTime parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setTimestamp(
        i,
        parameter != null ? new Timestamp(parameter.toInstant().toEpochMilli()) : null,
        UTC_CALENDAR);
  }

  @Override
  public OffsetDateTime getResult(ResultSet rs, String columnName) throws SQLException {
    return toOffsetDateTime(rs.getTimestamp(columnName, UTC_CALENDAR));
  }

  @Override
  public OffsetDateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
    return toOffsetDateTime(rs.getTimestamp(columnIndex, UTC_CALENDAR));
  }

  @Override
  public OffsetDateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toOffsetDateTime(cs.getTimestamp(columnIndex, UTC_CALENDAR));
  }

  private static OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
    return timestamp != null ? DateTimes.ofEpochMilli(timestamp.getTime()) : null;
  }
}
