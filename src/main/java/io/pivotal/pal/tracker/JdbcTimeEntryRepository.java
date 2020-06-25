package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository (DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("time_entries").usingGeneratedKeyColumns("id");
        Long id = simpleJdbcInsert.executeAndReturnKey(timeEntry.toMap()).longValue();
        return find(id);
    }

    @Override
    public TimeEntry find(long id) {
        try {
            String query = "SELECT * FROM time_entries WHERE id = ?;";
            return jdbcTemplate.queryForObject(query, this::mapRowToTimeEntry, id);
        } catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        String query = "SELECT * FROM time_entries;";
        return jdbcTemplate.query(query, this::mapRowToTimeEntry);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        String query = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?,  hours = ? WHERE id = ?";
        timeEntry.setId(id);
        jdbcTemplate.update(query, timeEntry.toObjectArr());
        return find(id);
    }

    @Override
    public TimeEntry delete(long id) {
        TimeEntry timeEntry = find(id);
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", id);
        return timeEntry;
    }

    private TimeEntry mapRowToTimeEntry(ResultSet resultSet, int rowNum) throws SQLException {
            TimeEntryRowMapper rowMapper = new TimeEntryRowMapper();
            return rowMapper.mapRow(resultSet, rowNum);

    }


}
