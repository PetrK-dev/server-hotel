package cz.cvut.fit.serverPart.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(name = "from_date")
    private Date from;

    @NotNull
    @Column(name = "to_date")
    private Date to;

    @ManyToOne
    @JoinColumn(name = "client_of_reservation_id")
    private Client clientOfReservation;

    @ManyToOne
    @JoinColumn(name = "apartment_of_reservation")
    private Apartment apartmentOfReservation;

    public Reservation() {
    }

    public Reservation(Date from, Date to, Client clientOfReservation, Apartment apartmentOfReservation) {
        this.from = from;
        this.to = to;
        this.clientOfReservation = clientOfReservation;
        this.apartmentOfReservation = apartmentOfReservation;
    }

    public int getId() {
        return id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Client getClientOfReservation() {
        return clientOfReservation;
    }

    public void setClientOfReservation(Client reservationOfClient) {
        this.clientOfReservation = reservationOfClient;
    }

    public Apartment getApartmentOfReservation() {
        return apartmentOfReservation;
    }

    public void setApartmentOfReservation(Apartment reservationOfApartment) {
        this.apartmentOfReservation = reservationOfApartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
