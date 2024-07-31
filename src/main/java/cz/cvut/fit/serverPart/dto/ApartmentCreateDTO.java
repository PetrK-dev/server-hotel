package cz.cvut.fit.serverPart.dto;

import java.util.List;
import java.util.Objects;

public class ApartmentCreateDTO {

    private final int price;
    private final String disposition;
    private final List<Integer> equipmentsIDs;

    public ApartmentCreateDTO(int price, String disposition, List<Integer> equipmentsIDs) {
        this.price = price;
        this.disposition = disposition;
        this.equipmentsIDs = equipmentsIDs;
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
        ApartmentCreateDTO that = (ApartmentCreateDTO) o;
        return price == that.price &&
                disposition.equals(that.disposition) &&
                Objects.equals(equipmentsIDs, that.equipmentsIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, disposition, equipmentsIDs);
    }
}
