package cz.cvut.fit.serverPart.dto;

import java.sql.Date;
import java.util.Objects;

public class ReservationCreateDTO {

    private final Date from;
    private final Date to;
    private final Integer clientOfReservationId;
    private final Integer apartmentOfReservationId;

    public ReservationCreateDTO(Date from, Date to, Integer reservationOfClientId, Integer reservationOfApartmentId) {
        this.from = from;
        this.to = to;
        this.clientOfReservationId = reservationOfClientId;
        this.apartmentOfReservationId = reservationOfApartmentId;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public Integer getClientOfReservationId() {
        return clientOfReservationId;
    }

    public Integer getApartmentOfReservationId() {
        return apartmentOfReservationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationCreateDTO that = (ReservationCreateDTO) o;
        return from.equals(that.from) &&
                to.equals(that.to) &&
                clientOfReservationId.equals(that.clientOfReservationId) &&
                apartmentOfReservationId.equals(that.apartmentOfReservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, clientOfReservationId, apartmentOfReservationId);
    }
}
