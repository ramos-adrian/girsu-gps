package ar.ramos.girsugps.internal.positionrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPositionRecordService {
    Page<PositionRecord> findAll(Pageable pageable);

    PositionRecord save(PositionRecord positionRecord);

    List<PositionRecord> findAllLatest();
}
