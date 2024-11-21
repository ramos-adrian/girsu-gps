package ar.ramos.girsugps.internal.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaceRepository extends JpaRepository<Place, String> {
}
