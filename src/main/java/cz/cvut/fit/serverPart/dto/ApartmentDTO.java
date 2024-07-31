package cz.cvut.fit.serverPart.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class ApartmentDTO extends RepresentationModel<ApartmentDTO> {

    private final int id;
    private final int price;
    private final String disposition;
    private final List<Integer> equipmentsIDs;

    public ApartmentDTO(int id, int price, String disposition) {
        this.id = id;
        this.price = price;
        this.disposition = disposition;
        this.equipmentsIDs = null;
    }

    public ApartmentDTO(int id, int price, String disposition, List<Integer> equipmentsIDs) {
        this.id = id;
        this.price = price;
        this.disposition = disposition;
        this.equipmentsIDs = equipmentsIDs;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getDisposition() {
        return disposition;
    }

    public List<Integer> getEquipmentsIDs() {
        return equipmentsIDs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentDTO that = (ApartmentDTO) o;
        return id == that.id &&
                price == that.price &&
                disposition.equals(that.disposition) &&
                Objects.equals(equipmentsIDs, that.equipmentsIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, disposition, equipmentsIDs);
    }
}
