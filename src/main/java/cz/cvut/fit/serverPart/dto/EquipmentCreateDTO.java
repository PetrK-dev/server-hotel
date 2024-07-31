package cz.cvut.fit.serverPart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class EquipmentCreateDTO {

    private final String name;

    public EquipmentCreateDTO(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentCreateDTO that = (EquipmentCreateDTO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
