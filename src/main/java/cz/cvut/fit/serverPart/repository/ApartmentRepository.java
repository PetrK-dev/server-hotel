package cz.cvut.fit.serverPart.repository;

import cz.cvut.fit.serverPart.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer>{

    //vsechny apartmany co maji velikost = disposition ale nebyly jeste nikdy rezervovany
    @Query(
            "SELECT A from Apartment A  " +
            "WHERE A.id not in (SELECT R.apartmentOfReservation FROM Reservation R) and A.disposition = :disposition"
    )
    List<Apartment> findAllWithDisposition(String disposition);

    //nalezne vsechny apartmany, ktery si dany klient nekdy rezervoval
    @Query(
            "SELECT DISTINCT A from Apartment A  JOIN Reservation R ON A = R.apartmentOfReservation " +
            "WHERE R.clientOfReservation.id = :idOfClient"
    )
    List<Apartment> findAllWithClientsHistory(int idOfClient);
}
