package ar.ramos.girsugps.internal.positionrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPositionRecordRepository extends CrudRepository<PositionRecord, Long>, PagingAndSortingRepository<PositionRecord, Long> {
    Page<PositionRecord> findPositionRecordsByTruckId(Long truckId, PageRequest pageRequest);
}
