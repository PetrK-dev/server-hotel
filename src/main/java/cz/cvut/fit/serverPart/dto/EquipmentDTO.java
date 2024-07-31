package cz.cvut.fit.serverPart.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class EquipmentDTO extends RepresentationModel<EquipmentDTO> {

    private final int id;
    private final String name;

    public EquipmentDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentDTO that = (EquipmentDTO) o;
        return id == that.id &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
