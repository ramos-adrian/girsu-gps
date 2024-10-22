package ar.ramos.girsugps.internal.positionrecord;

import ar.ramos.girsugps.internal.truck.TruckLocationUpdateDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PositionRecordService implements IPositionRecordService {

    private final IPositionRecordRepository positionRecordRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PositionRecordService(IPositionRecordRepository positionRecordRepository, ApplicationEventPublisher eventPublisher) {
        this.positionRecordRepository = positionRecordRepository;
        this.eventPublisher = eventPublisher;
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
        PositionRecord savedRecord = positionRecordRepository.save(positionRecord);
        publishNewPositionEvent(savedRecord);
        return savedRecord;
    }

    private void publishNewPositionEvent(PositionRecord savedRecord) {
        TruckLocationUpdateDTO update = new TruckLocationUpdateDTO(
                savedRecord.getId(),
                savedRecord.getTruckId(),
                savedRecord.getLatitude(),
                savedRecord.getLongitude(),
                savedRecord.getTimestamp()
        );
        eventPublisher.publishEvent(update);
    }
}
