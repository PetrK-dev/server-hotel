package cz.cvut.fit.serverPart.dto;

import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.util.Objects;

public class ReservationDTO extends RepresentationModel<ReservationDTO> {

    private final int id;
    private final Date from;
    private final Date to;
    private final Integer clientOfReservationId;
    private final Integer apartmentOfReservationId;

    public ReservationDTO(int id, Date from, Date to, Integer reservationOfClientId, Integer reservationOfApartmentId) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.clientOfReservationId = reservationOfClientId;
        this.apartmentOfReservationId = reservationOfApartmentId;
    }

    public int getId() {
        return id;
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
        ReservationDTO that = (ReservationDTO) o;
        return id == that.id &&
                from.equals(that.from) &&
                to.equals(that.to) &&
                clientOfReservationId.equals(that.clientOfReservationId) &&
                apartmentOfReservationId.equals(that.apartmentOfReservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, clientOfReservationId, apartmentOfReservationId);
    }
}
