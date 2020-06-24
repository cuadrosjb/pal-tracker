package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private Map<Long, TimeEntry> database = new HashMap<>();
    private Long index = 0L;

    public TimeEntry create(TimeEntry timeEntry) {
        Long id = findNextKey();
        timeEntry.setId(id);
        database.put(id, timeEntry);
        return database.get(id);
    }

    private Long findNextKey() {
        return ++index;
    }

    public TimeEntry find(long id) {
        return database.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(database.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        database.replace(id, timeEntry);
        return database.get(id);
    }

    public TimeEntry delete(long id) {
        return database.remove(id);
    }
}
