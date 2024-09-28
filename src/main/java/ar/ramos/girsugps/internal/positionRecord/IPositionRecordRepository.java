package ar.ramos.girsugps.internal.positionRecord;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPositionRecordRepository extends CrudRepository<PositionRecord, Long>, PagingAndSortingRepository<PositionRecord, Long> {

}
