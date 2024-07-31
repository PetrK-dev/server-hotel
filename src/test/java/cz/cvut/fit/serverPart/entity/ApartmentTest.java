package cz.cvut.fit.serverPart.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentTest {

    @Test
    void getPrice() {
        Apartment apartment = new Apartment(8500, "2+1", null);
        assertEquals(8500, apartment.getPrice());
    }

    @Test
    void setPrice() {
        Apartment apartment = new Apartment(8500, "2+1", null);
        apartment.setPrice(9000);
        assertEquals(9000, apartment.getPrice());
    }

    @Test
    void getDisposition() {
        Apartment apartment = new Apartment(8500, "2+1", null);
        assertEquals("2+1", apartment.getDisposition());
    }

    @Test
    void setDisposition() {
        Apartment apartment = new Apartment(8500, "2+1", null);
        apartment.setDisposition("2+1+1");
        assertEquals("2+1+1", apartment.getDisposition());
    }

    @Test
    void getEquipments() {
        Equipment equipment1 = new Equipment("table");
        Equipment equipment2 = new Equipment("chair");
        List<Equipment> equipments = new ArrayList<>();
        equipments.add(equipment1);
        equipments.add(equipment2);

        Apartment apartment1 = new Apartment(8500, "2+1", equipments);
        Apartment apartment2 = new Apartment(8500, "2+1", null);
        assertEquals(equipments, apartment1.getEquipments());
        assertEquals(null, apartment2.getEquipments());
    }

    @Test
    void setEquipments() {
        Equipment equipment1 = new Equipment("table");
        Equipment equipment2 = new Equipment("chair");
        List<Equipment> equipments1 = new ArrayList<>();
        List<Equipment> equipments2 = new ArrayList<>();
        equipments1.add(equipment1);
        equipments1.add(equipment2);
        equipments2.add(equipment1);

        Apartment apartment1 = new Apartment(8500, "2+1", equipments1);
        Apartment apartment2 = new Apartment(8500, "2+1", null);
        apartment1.setEquipments(equipments2);
        apartment2.setEquipments(equipments2);
        assertEquals(equipments2, apartment1.getEquipments());
        assertEquals(equipments2, apartment2.getEquipments());
    }

    @Test
    void testEquals() {
        Equipment equipment1 = new Equipment("table");
        List<Equipment> equipments2 = new ArrayList<>();
        equipments2.add(equipment1);

        Apartment apartment1 = new Apartment(8500, "2+1", null);
        Apartment apartment2 = new Apartment(8500, "2+1", null);

        assertEquals( apartment1, apartment2 );

        apartment1.setEquipments(equipments2);
        apartment2.setEquipments(equipments2);

        assertEquals( apartment1, apartment2 );

    }
}