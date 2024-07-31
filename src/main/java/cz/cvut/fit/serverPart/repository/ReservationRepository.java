package cz.cvut.fit.serverPart.repository;

import cz.cvut.fit.serverPart.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
