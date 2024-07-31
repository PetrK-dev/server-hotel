package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.EquipmentCreateDTO;
import cz.cvut.fit.serverPart.dto.EquipmentDTO;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional(readOnly = true)
    public List<EquipmentDTO> findAll()  {
        return equipmentRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Equipment> findByIds(List<Integer> ids) {
        return equipmentRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Optional<Equipment> findById(int id) {
        return equipmentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<EquipmentDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }

    @Transactional
    public EquipmentDTO create(EquipmentCreateDTO equipmentDTO) {
        return toDTO(equipmentRepository.save(
                new Equipment(equipmentDTO.getName())
                )
        );
    }

    @Transactional
    public EquipmentDTO update(int id, EquipmentCreateDTO equipmentDTO) throws EntityNotFoundException {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
        if (optionalEquipment.isEmpty())
            throw new EntityNotFoundException("no such equipment");
        Equipment equipment= optionalEquipment.get();
        equipment.setName(equipmentDTO.getName());
        return toDTO(equipment);
    }

    @Transactional
    public void deleteById( int id)throws EntityNotFoundException{
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
        if (optionalEquipment.isEmpty())
            throw new EntityNotFoundException("no such equipment");
        Equipment equipment = optionalEquipment.get();
        equipmentRepository.delete(equipment);
    }

    private EquipmentDTO toDTO(Equipment equipment) {
        return new EquipmentDTO(equipment.getId(), equipment.getName());
    }

    private Optional<EquipmentDTO> toDTO(Optional<Equipment> equipment) {
        if (equipment.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(equipment.get()));
    }
}
