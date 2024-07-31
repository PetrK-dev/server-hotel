package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.EquipmentCreateDTO;
import cz.cvut.fit.serverPart.dto.EquipmentDTO;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.service.EquipmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EquipmentControllerTest {

    @Autowired
    private EquipmentController equipmentController;

    @MockBean
    private EquipmentService equipmentService;

    @Test
    void readAll() {
        List<EquipmentDTO> equipmentsDTO = new ArrayList<>();
        EquipmentDTO equipmentDTO0 = new EquipmentDTO(0,"table");
        EquipmentDTO equipmentDTO1 = new EquipmentDTO(1,"chair");
        EquipmentDTO equipmentDTO2 = new EquipmentDTO(2,"lamp");
        equipmentsDTO.add(equipmentDTO0);
        equipmentsDTO.add(equipmentDTO1);
        equipmentsDTO.add(equipmentDTO2);

        BDDMockito.given(equipmentService.findAll()).willReturn(equipmentsDTO);

        List<EquipmentDTO> equipmentsDTOList = equipmentController.readAll();
        Assertions.assertEquals(equipmentsDTOList.size(), equipmentsDTO.size());
        Assertions.assertEquals(equipmentsDTOList.get(0).getId(), equipmentsDTO.get(0).getId());
        Assertions.assertEquals(equipmentsDTOList.get(1).getId(), equipmentsDTO.get(1).getId());
        Assertions.assertEquals(equipmentsDTOList.get(2).getId(), equipmentsDTO.get(2).getId());
        Assertions.assertEquals(equipmentsDTOList.get(0).getName(), equipmentsDTO.get(0).getName());
        Assertions.assertEquals(equipmentsDTOList.get(1).getName(), equipmentsDTO.get(1).getName());
        Assertions.assertEquals(equipmentsDTOList.get(2).getName(), equipmentsDTO.get(2).getName());

        Mockito.verify(equipmentService, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void readOneById() {
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);
        EquipmentDTO equipmentDTO = new EquipmentDTO(equipment.getId(),equipment.getName());

        BDDMockito.given(equipmentService.findByIdAsDTO(equipmentDTO.getId())).willReturn(Optional.of(equipmentDTO));

        Assertions.assertEquals(equipmentDTO, equipmentController.readOneById(equipment.getId()));

        Mockito.verify(equipmentService, Mockito.atLeastOnce()).findByIdAsDTO(equipment.getId());
    }

    @Test
    void create() {
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);
        EquipmentDTO equipmentDTO = new EquipmentDTO(equipment.getId(),equipment.getName());
        EquipmentCreateDTO equipmentCreateDTO = new EquipmentCreateDTO("table");

        BDDMockito.given(equipmentService.create(equipmentCreateDTO)).willReturn(equipmentDTO);

        Assertions.assertEquals(equipmentDTO, equipmentController.create(equipmentCreateDTO));

        Mockito.verify(equipmentService, Mockito.atLeastOnce()).create(equipmentCreateDTO);
    }

    @Test
    void update(){
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);
        EquipmentCreateDTO equipmentCreateDTO = new EquipmentCreateDTO("tableUpdate");
        EquipmentDTO equipmentDTO = new EquipmentDTO(equipment.getId(),"tableUpdate");

        BDDMockito.given(equipmentService.update(equipment.getId(), equipmentCreateDTO)).willReturn(equipmentDTO);

        Assertions.assertEquals(equipmentDTO, equipmentController.update(equipment.getId(), equipmentCreateDTO));

        Mockito.verify(equipmentService, Mockito.atLeastOnce()).update(equipment.getId(), equipmentCreateDTO);
    }

    @Test
    void delete(){
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);

        BDDMockito.given(equipmentService.findById(equipment.getId())).willReturn(Optional.of(equipment));

        equipmentController.deleteById(equipment.getId());

        Mockito.verify(equipmentService, Mockito.atLeastOnce()).deleteById(equipment.getId());
    }
}