package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private DistributionSummary distributionSummary;
    private Counter counter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        distributionSummary = meterRegistry.summary("timeEntry.distributionSummary");
        counter = meterRegistry.counter("timeEntry.operationCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry entity = timeEntryRepository.create(timeEntry);
        distributionSummary.record(timeEntryRepository.list().size());
        counter.increment();
        return new ResponseEntity<TimeEntry>( entity, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry entity = timeEntryRepository.find(id);
        if (entity != null){
            counter.increment();
            return new ResponseEntity<TimeEntry>( entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>( entity, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>( timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry result = timeEntryRepository.update(id, timeEntry);
        if (result != null){
            counter.increment();
            return new ResponseEntity<TimeEntry>( result, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>( result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        counter.increment();
        distributionSummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
