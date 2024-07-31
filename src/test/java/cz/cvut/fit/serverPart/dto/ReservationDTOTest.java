package cz.cvut.fit.serverPart.dto;

import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDTOTest {

    @Test
    void getId() {
    }

    @Test
    void getFrom() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationDTO reservationDTO = new ReservationDTO( 0,date1, date2, client.getId(), apartment.getId());
        assertEquals(date1, reservationDTO.getFrom());
    }

    @Test
    void getTo() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationDTO reservationDTO = new ReservationDTO( 0,date1, date2, client.getId(), apartment.getId());
        assertEquals(date2, reservationDTO.getTo());
    }

    @Test
    void getReservationOfClientId() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationDTO reservationDTO = new ReservationDTO( 0,date1, date2, client.getId(), apartment.getId());
        assertEquals(client.getId(), reservationDTO.getClientOfReservationId());
    }

    @Test
    void getReservationDTOOfApartmentId() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationDTO reservationDTO = new ReservationDTO( 0,date1, date2, client.getId(), apartment.getId());
        assertEquals(apartment.getId(), reservationDTO.getApartmentOfReservationId());
    }

    @Test
    void testEquals() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationDTO reservationDTO1 = new ReservationDTO( 0,date1, date2, client.getId(), apartment.getId());
        ReservationDTO reservationDTO2 = new ReservationDTO( 0,date1, date2, client.getId(), apartment.getId());
        assertEquals( reservationDTO1, reservationDTO2 );
    }
}