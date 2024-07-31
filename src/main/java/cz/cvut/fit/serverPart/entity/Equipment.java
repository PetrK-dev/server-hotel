package cz.cvut.fit.serverPart.entity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Equipment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    public Equipment() {}

    public Equipment(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
