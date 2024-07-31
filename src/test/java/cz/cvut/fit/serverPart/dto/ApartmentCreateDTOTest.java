package cz.cvut.fit.serverPart.dto;

import cz.cvut.fit.serverPart.entity.Equipment;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentCreateDTOTest {

    @Test
    void getPrice() {
        ApartmentCreateDTO apartmentCreateDTO = new ApartmentCreateDTO(8500, "2+1", null);
        assertEquals(8500, apartmentCreateDTO.getPrice());
    }

    @Test
    void getDisposition() {
        ApartmentCreateDTO apartmentCreateDTO = new ApartmentCreateDTO(8500, "2+1", null);
        assertEquals("2+1", apartmentCreateDTO.getDisposition());
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

        ApartmentCreateDTO apartmentCreateDTO1 = new ApartmentCreateDTO(8500, "2+1", equipments);
        ApartmentCreateDTO apartmentCreateDTO2 = new ApartmentCreateDTO(8500, "2+1", null);
        assertEquals(equipments, apartmentCreateDTO1.getEquipmentsIDs());
        assertEquals(null, apartmentCreateDTO2.getEquipmentsIDs());
    }

    @Test
    void testEquals() {
        ApartmentCreateDTO apartmentCreateDTO1 = new ApartmentCreateDTO(8500, "2+1", null);
        ApartmentCreateDTO apartmentCreateDTO2 = new ApartmentCreateDTO(8500, "2+1", null);
        assertEquals( apartmentCreateDTO1, apartmentCreateDTO2 );
    }
}