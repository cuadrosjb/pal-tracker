package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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

        timeEntry.setId(id);

        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        String query = "SELECT * FROM time_entries WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, BeanPropertyRowMapper.newInstance(TimeEntry.class));
    }

    @Override
    public List<TimeEntry> list() {
        return null;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        return null;
    }

    @Override
    public TimeEntry delete(long id) {
        return null;
    }


}
