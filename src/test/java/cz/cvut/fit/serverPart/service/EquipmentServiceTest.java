package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.EquipmentCreateDTO;
import cz.cvut.fit.serverPart.dto.EquipmentDTO;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.repository.EquipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;

@SpringBootTest
class EquipmentServiceTest {

    @Autowired
    private EquipmentService equipmentService;

    @MockBean
    private EquipmentRepository equipmentRepository;

    @Test
    void findAll() {
        List<Equipment> equipments = new ArrayList<>();
        Equipment equipment0 = new Equipment("table");
        ReflectionTestUtils.setField(equipment0, "id", 0);
        Equipment equipment1 = new Equipment("chair");
        ReflectionTestUtils.setField(equipment1, "id", 1);
        Equipment equipment2 = new Equipment("lamp");
        ReflectionTestUtils.setField(equipment2, "id", 2);
        equipments.add(equipment0);
        equipments.add(equipment1);
        equipments.add(equipment2);

        BDDMockito.given(equipmentRepository.findAll()).willReturn(equipments);

        List<EquipmentDTO> equipmentsDTOList = equipmentService.findAll();

        Assertions.assertEquals(equipmentsDTOList.size(), equipments.size());
        Assertions.assertEquals(equipmentsDTOList.get(0).getId(), equipment0.getId());
        Assertions.assertEquals(equipmentsDTOList.get(1).getId(), equipment1.getId());
        Assertions.assertEquals(equipmentsDTOList.get(2).getId(), equipment2.getId());
        Assertions.assertEquals(equipmentsDTOList.get(0).getName(), equipment0.getName());
        Assertions.assertEquals(equipmentsDTOList.get(1).getName(), equipment1.getName());
        Assertions.assertEquals(equipmentsDTOList.get(2).getName(), equipment2.getName());

        Mockito.verify(equipmentRepository, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void findByIds() {
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        Equipment equipment0 = new Equipment("table");
        ReflectionTestUtils.setField(equipment0, "id", 0);
        Equipment equipment1 = new Equipment("chair");
        ReflectionTestUtils.setField(equipment1, "id", 1);
        Equipment equipment2 = new Equipment("seat");
        ReflectionTestUtils.setField(equipment2, "id", 2);
        equipmentsIds.add(equipment0.getId());
        equipmentsIds.add(equipment1.getId());
        equipmentsIds.add(equipment2.getId());
        equipments.add(equipment0);
        equipments.add(equipment1);
        equipments.add(equipment2);

        BDDMockito.given(equipmentRepository.findAllById(equipmentsIds)).willReturn(equipments);

        List<Equipment> equipmentsList = equipmentService.findByIds(equipmentsIds);

        Assertions.assertEquals(equipmentsList.size(), equipments.size());
        Assertions.assertEquals(equipmentsList.get(0), equipment0);
        Assertions.assertEquals(equipmentsList.get(1), equipment1);
        Assertions.assertEquals(equipmentsList.get(2), equipment2);

        Mockito.verify(equipmentRepository, Mockito.atLeastOnce()).findAllById(equipmentsIds);
    }

    @Test
    void findById() {
        Equipment equipment = new Equipment("table");

        BDDMockito.given(equipmentRepository.findById(equipment.getId())).willReturn(Optional.of(equipment));
        Optional<Equipment> equipmentOptional = equipmentService.findById(equipment.getId());

        if(equipmentOptional.isPresent()) {
            Equipment equipment1 = equipmentOptional.get();
            Assertions.assertEquals(equipment, equipment1);
        }
        else
            throw new EntityNotFoundException("Equipment service returned null");

        Mockito.verify(equipmentRepository,Mockito.atLeastOnce()).findById(equipment.getId());
    }

    @Test
    void findByIdAsDTO() {
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);
        EquipmentDTO equipmentDTO = new EquipmentDTO(equipment.getId(),"table");

        BDDMockito.given(equipmentRepository.findById(equipment.getId())).willReturn(Optional.of(equipment));

        if(equipmentService.findByIdAsDTO(equipment.getId()).isPresent()) {
            EquipmentDTO equipmentDTO1 = equipmentService.findByIdAsDTO(equipment.getId()).get();
            Assertions.assertEquals(equipmentDTO, equipmentDTO1);
        }
        else
            throw new EntityNotFoundException("Equipment service returned null");

        Mockito.verify(equipmentRepository,Mockito.atLeastOnce()).findById(equipment.getId());
    }

    @Test
    void create() {
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);
        EquipmentDTO equipmentDTO = new EquipmentDTO(equipment.getId(),equipment.getName());
        EquipmentCreateDTO equipmentCreateDTO = new EquipmentCreateDTO("table");
        BDDMockito.given(equipmentRepository.save((Equipment)notNull())).willReturn(equipment);
        Assertions.assertEquals(equipmentDTO, equipmentService.create(equipmentCreateDTO));
        Mockito.verify(equipmentRepository, Mockito.atLeastOnce()).save((Equipment)notNull());
    }

    @Test
    void update(){
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);
        EquipmentCreateDTO equipmentCreateDTOUpdate = new EquipmentCreateDTO("chair");

        BDDMockito.given(equipmentRepository.findById(equipment.getId())).willReturn(Optional.of(equipment));

        EquipmentDTO equipmentDTO;
        try {
            equipmentDTO = equipmentService.update(equipment.getId(), equipmentCreateDTOUpdate);
        }catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("Equipment service returned Exception");
        }

        Assertions.assertEquals(equipmentDTO.getId(), equipment.getId());
        Assertions.assertEquals(equipmentDTO.getName(), equipment.getName());

        Mockito.verify(equipmentRepository,Mockito.atLeastOnce()).findById(equipment.getId());
    }

    @Test
    void deleteById(){
        Equipment equipment = new Equipment("table");
        ReflectionTestUtils.setField(equipment, "id", 0);

        BDDMockito.given(equipmentRepository.findById(equipment.getId())).willReturn(Optional.of(equipment));

        try {
            equipmentService.deleteById(0);
        }
        catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Equipment service returned Exception");
        }
        Mockito.verify(equipmentRepository,Mockito.atLeastOnce()).findById(equipment.getId());
        Mockito.verify(equipmentRepository, Mockito.atLeastOnce()).delete((Equipment)notNull());
    }
}