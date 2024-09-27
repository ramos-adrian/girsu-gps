package ar.ramos.girsugps.internal.truck;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ITruckRepository extends CrudRepository<Truck, Long>, PagingAndSortingRepository<Truck, Long> {
}
