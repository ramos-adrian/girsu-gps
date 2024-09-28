package ar.ramos.girsugps.internal.positionRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PositionRecordService implements IPositionRecordService {

    private final IPositionRecordRepository positionRecordRepository;

    public PositionRecordService(IPositionRecordRepository positionRecordRepository) {
        this.positionRecordRepository = positionRecordRepository;
    }

    @Override
    public Page<PositionRecord> findAll(Pageable pageable) {
        return positionRecordRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Order.desc("timestamp")))
                )
        );
    }

    @Override
    public PositionRecord save(PositionRecord positionRecord) {
        return positionRecordRepository.save(positionRecord);
    }
}
