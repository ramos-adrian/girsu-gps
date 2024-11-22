package ar.ramos.girsugps.internal.user;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@Profile("prod")
public interface IUserHomeRepository extends JpaRepository<UserHome, Long> {
}
