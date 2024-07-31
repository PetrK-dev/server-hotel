package cz.cvut.fit.serverPart.dto;

import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationCreateDTOTest {

    @Test
    void getFrom() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO( date1, date2, client.getId(), apartment.getId());
        assertEquals(date1, reservationCreateDTO.getFrom());
    }

    @Test
    void getTo() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO( date1, date2, client.getId(), apartment.getId());
        assertEquals(date2, reservationCreateDTO.getTo());
    }

    @Test
    void getReservationOfClientId() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO( date1, date2, client.getId(), apartment.getId());
        assertEquals(client.getId(), reservationCreateDTO.getClientOfReservationId());
    }

    @Test
    void getReservationOfApartmentId() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO( date1, date2, client.getId(), apartment.getId());
        assertEquals(apartment.getId(), reservationCreateDTO.getApartmentOfReservationId());
    }

    @Test
    void testEquals() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        ReservationCreateDTO reservationCreateDTO1 = new ReservationCreateDTO( date1, date2, client.getId(), apartment.getId());
        ReservationCreateDTO reservationCreateDTO2 = new ReservationCreateDTO( date1, date2, client.getId(), apartment.getId());
        assertEquals( reservationCreateDTO1, reservationCreateDTO2 );
    }
}