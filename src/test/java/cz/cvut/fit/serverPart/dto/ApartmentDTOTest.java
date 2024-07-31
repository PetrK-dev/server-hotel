package cz.cvut.fit.serverPart.dto;

import cz.cvut.fit.serverPart.entity.Equipment;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentDTOTest {

    @Test
    void getId() {
        ApartmentDTO apartmentDTO = new ApartmentDTO(0, 8500, "2+1", null);
        assertEquals(0, apartmentDTO.getId());
    }

    @Test
    void getPrice() {
        ApartmentDTO apartmentDTO = new ApartmentDTO(0, 8500, "2+1", null);
        assertEquals(8500, apartmentDTO.getPrice());
    }

    @Test
    void getDisposition() {
        ApartmentDTO apartmentDTO = new ApartmentDTO(0, 8500, "2+1", null);
        assertEquals("2+1", apartmentDTO.getDisposition());
    }

    @Test
    void getEquipmentsIDs() {
        Equipment equipment1 = new Equipment("table");
        ReflectionTestUtils.setField(equipment1, "id", 0);
        Equipment equipment2 = new Equipment("chair");
        ReflectionTestUtils.setField(equipment2, "id", 1);
        List<Integer> equipments = new ArrayList<>();
        equipments.add(equipment1.getId());
        equipments.add(equipment2.getId());

        ApartmentDTO apartmentDTO1 = new ApartmentDTO(0,8500, "2+1", equipments);
        ApartmentDTO apartmentDTO2 = new ApartmentDTO(1,8500, "2+1", null);
        assertEquals(equipments, apartmentDTO1.getEquipmentsIDs());
        assertEquals(null, apartmentDTO2.getEquipmentsIDs());
    }

    @Test
    void testEquals() {
        ApartmentDTO apartmentDTO1 = new ApartmentDTO(0,8500, "2+1", null);
        ApartmentDTO apartmentDTO2 = new ApartmentDTO(0,8500, "2+1", null);
        assertEquals( apartmentDTO1, apartmentDTO2 );
    }
}