package ar.ramos.girsugps.internal.truck;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// TODO Remove paging and sorting repository if not needed. Trucks are not expected to be paginated because they are not expected to be too many.
public interface ITruckRepository extends CrudRepository<Truck, Long>, PagingAndSortingRepository<Truck, Long> {
}
