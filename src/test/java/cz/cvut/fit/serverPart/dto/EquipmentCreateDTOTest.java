package cz.cvut.fit.serverPart.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentCreateDTOTest {

    @Test
    void getName() {
        EquipmentCreateDTO equipmentCreateDTO = new EquipmentCreateDTO("table");
        assertEquals("table", equipmentCreateDTO.getName());
    }

    @Test
    void testEquals() {
        EquipmentCreateDTO equipmentCreateDTO1 = new EquipmentCreateDTO("table");
        EquipmentCreateDTO equipmentCreateDTO2 = new EquipmentCreateDTO("table");
        assertEquals( equipmentCreateDTO1, equipmentCreateDTO2 );
    }
}