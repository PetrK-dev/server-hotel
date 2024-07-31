package cz.cvut.fit.serverPart.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentTest {

    @Test
    void getName() {
        Equipment equipment= new Equipment("table");
        assertEquals("table", equipment.getName());
    }

    @Test
    void setName() {
        Equipment equipment= new Equipment("table");
        equipment.setName("chair");
        assertEquals("chair", equipment.getName());
    }

    @Test
    void testEquals() {
        Equipment equipment1 = new Equipment("table");
        Equipment equipment2 = new Equipment("table");
        assertEquals( equipment1, equipment2 );
    }
}