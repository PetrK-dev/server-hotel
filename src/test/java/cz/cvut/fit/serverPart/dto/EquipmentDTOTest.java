package cz.cvut.fit.serverPart.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentDTOTest {

    @Test
    void getId() {
        EquipmentDTO equipmentDTO = new EquipmentDTO(0,"table");
        assertEquals(0 , equipmentDTO.getId());
    }

    @Test
    void getName() {
        EquipmentDTO equipmentDTO = new EquipmentDTO(0,"table");
        assertEquals("table", equipmentDTO.getName());
    }

    @Test
    void testEquals() {
        EquipmentDTO equipmentDTO1 = new EquipmentDTO(0,"table");
        EquipmentDTO equipmentDTO2 = new EquipmentDTO(0,"table");
        assertEquals( equipmentDTO1, equipmentDTO2 );
    }

}