package ar.ramos.girsugps.internal.positionRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/positionRecords")
public class PositionRecordController {

    private final IPositionRecordService positionRecordService;

    public PositionRecordController(IPositionRecordService positionRecordService) {
        this.positionRecordService = positionRecordService;
    }

    @GetMapping
    public ResponseEntity<List<PositionRecord>> findAll(Pageable pageable) {
        Page<PositionRecord> records = positionRecordService.findAll(pageable);
        return ResponseEntity.ok(records.getContent());
    }

    @PostMapping
    public ResponseEntity<PositionRecord> save(@RequestBody PositionRecord record, UriComponentsBuilder ucb) {
        PositionRecord savedRecord = positionRecordService.save(record);
        URI location = ucb.path("/positionRecord/{id}").buildAndExpand(savedRecord.getId()).toUri();
        return ResponseEntity.created(location).body(savedRecord);
    }

}
