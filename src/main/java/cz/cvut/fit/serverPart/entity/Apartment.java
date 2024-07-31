package cz.cvut.fit.serverPart.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Apartment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private int price;

    @NotNull
    private String disposition;

    @ManyToMany
    @JoinTable(
        name = "apartment_equipment",
        joinColumns = @JoinColumn(name = "apartment_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipments;

    public Apartment(){}

    public Apartment(int price, String disposition, List<Equipment> equipments) {
        this.price = price;
        this.disposition = disposition;
        this.equipments = equipments;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
