package cz.cvut.fit.serverPart.entity;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void getFrom(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment);
        assertEquals(date1, reservation.getFrom());
    }

    @Test
    void setFrom() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date12 = new Date(2019-12-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment);
        reservation.setFrom(date12);
        assertEquals(date12, reservation.getFrom());
    }

    @Test
    void getTo() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment);
        assertEquals(date2, reservation.getTo());
    }

    @Test
    void setTo() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date12 = new Date(2020-12-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment);
        reservation.setTo(date12);
        assertEquals(date12, reservation.getTo());
    }

    @Test
    void getReservationOfClient() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment);
        assertEquals(client, reservation.getClientOfReservation());
    }

    @Test
    void setReservationOfClient() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client1 = new Client("Mr", "Nobody");
        Client client2 = new Client("Mr", "Second");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client1, apartment);
        reservation.setClientOfReservation(client2);
        assertEquals(client2, reservation.getClientOfReservation());
    }

    @Test
    void getReservationOfApartment() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment);
        assertEquals(apartment, reservation.getApartmentOfReservation());
    }

    @Test
    void setReservationOfApartment() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment1 = new Apartment(8500, "2+1", null);
        Apartment apartment2 = new Apartment(9000, "3+1", null);
        Reservation reservation = new Reservation( date1, date2, client, apartment1);
        reservation.setApartmentOfReservation(apartment2);
        assertEquals(apartment2, reservation.getApartmentOfReservation());
    }

    @Test
    void testEquals() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Client client = new Client("Mr", "Nobody");
        Apartment apartment = new Apartment(8500, "2+1", null);
        Reservation reservation1 = new Reservation( date1, date2, client, apartment);
        Reservation reservation2 = new Reservation( date1, date2, client, apartment);
        assertEquals( reservation1, reservation2 );
    }
}