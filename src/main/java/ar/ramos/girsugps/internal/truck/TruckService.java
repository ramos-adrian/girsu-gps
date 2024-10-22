package ar.ramos.girsugps.internal.truck;

import ar.ramos.girsugps.internal.positionrecord.IPositionRecordRepository;
import ar.ramos.girsugps.internal.positionrecord.PositionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TruckService implements ITruckService {

    private final ITruckRepository truckRepository;
    private final IPositionRecordRepository positionRecordRepository;

    public TruckService(ITruckRepository truckRepository, IPositionRecordRepository positionRecordRepository) {
        this.truckRepository = truckRepository;
        this.positionRecordRepository = positionRecordRepository;
    }

    @Override
    public Truck findById(Long id) {
        return truckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Truck not found with id: " + id));
    }

    @Override
    public Truck save(Truck truck) {
        return truckRepository.save(truck);
    }

    @Override
    public Page<Truck> findAll(Pageable pageable) {
        return truckRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                )
        );
    }

    @Override
    public Page<PositionRecord> findPositionRecordsByTruckId(Long truckId, Pageable pageable) {
        return positionRecordRepository.findPositionRecordsByTruckId(
                truckId,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "timestamp"))
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        truckRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return truckRepository.existsById(id);
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }
}
