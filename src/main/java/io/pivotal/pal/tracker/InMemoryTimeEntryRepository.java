package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private Map<Long, TimeEntry> database = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry) {
        Long id = findNextKey();
        timeEntry.setId(id);
        TimeEntry entity = database.put(id, timeEntry);
        return entity;
    }

    private Long findNextKey() {
        try {
            return Collections.max(database.keySet());
        } catch (NoSuchElementException ex){
            return 0L;
        }
    }

    public TimeEntry find(long id) {
        return database.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(database.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        database.put(id, timeEntry);
        return database.get(id);
    }

    public void delete(long id) {
        database.remove(id);
    }
}
