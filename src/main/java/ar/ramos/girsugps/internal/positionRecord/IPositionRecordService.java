package ar.ramos.girsugps.internal.positionRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPositionRecordService {
    Page<PositionRecord> findAll(Pageable pageable);

    PositionRecord save(PositionRecord positionRecord);
}
