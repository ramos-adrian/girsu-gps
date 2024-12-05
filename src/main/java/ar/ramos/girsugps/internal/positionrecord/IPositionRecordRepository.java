package ar.ramos.girsugps.internal.positionrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IPositionRecordRepository extends CrudRepository<PositionRecord, Long>, PagingAndSortingRepository<PositionRecord, Long> {
    Page<PositionRecord> findPositionRecordsByTruckId(Long truckId, PageRequest pageRequest);

    @Query("SELECT pr FROM PositionRecord pr WHERE pr.timestamp IN (SELECT MAX(pr2.timestamp) FROM PositionRecord pr2 GROUP BY pr2.truckId)")
    List<PositionRecord> findAllLatest();
}
