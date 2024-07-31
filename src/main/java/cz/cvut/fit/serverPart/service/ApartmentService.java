package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.ApartmentCreateDTO;
import cz.cvut.fit.serverPart.dto.ApartmentDTO;
import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final EquipmentService equipmentService;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository, EquipmentService equipmentService) {
        this.apartmentRepository = apartmentRepository;
        this.equipmentService = equipmentService;
    }

    @Transactional(readOnly = true)
    public List<ApartmentDTO> findAll() {
        return apartmentRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Apartment> findByIds(List<Integer> ids) {
        return apartmentRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Optional<Apartment> findById(int id) {
        return apartmentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ApartmentDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public List<ApartmentDTO> findAllWithDisposition(String disposition){
        return apartmentRepository.findAllWithDisposition(disposition).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApartmentDTO> findAllWithClientsHistory(int idOfClient){
        return apartmentRepository.findAllWithClientsHistory(idOfClient).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ApartmentDTO create(ApartmentCreateDTO apartmentDTO) throws EntityNotFoundException{
        List<Equipment> equipments = equipmentService.findByIds( apartmentDTO.getEquipmentsIDs());
        if (equipments.size() != apartmentDTO.getEquipmentsIDs().size())
            throw new EntityNotFoundException("some equipments not found");

        Apartment apartment = new Apartment(apartmentDTO.getPrice(), apartmentDTO.getDisposition(), equipments);

        return toDTO(apartmentRepository.save(apartment));
    }

    @Transactional
    public ApartmentDTO update(int id, ApartmentCreateDTO apartmentDTO) throws EntityNotFoundException {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(id);
        if (optionalApartment.isEmpty())
            throw new EntityNotFoundException("no such apartment");
        Apartment apartment = optionalApartment.get();
        apartment.setPrice(apartmentDTO.getPrice());
        apartment.setDisposition(apartmentDTO.getDisposition());

        List<Equipment> equipments = equipmentService.findByIds( apartmentDTO.getEquipmentsIDs());
        if (equipments.size() != apartmentDTO.getEquipmentsIDs().size())
            throw new EntityNotFoundException("some equipments not found");
        apartment.setEquipments(equipments);

        return toDTO(apartment);
    }

    @Transactional
    public void deleteById( int id)throws EntityNotFoundException{
        Optional<Apartment> optionalApartment = apartmentRepository.findById(id);
        if (optionalApartment.isEmpty())
            throw new EntityNotFoundException("no such apartment");
        Apartment apartment = optionalApartment.get();
        apartmentRepository.delete(apartment);
    }

    private ApartmentDTO toDTO(Apartment apartment) {
        return new ApartmentDTO(apartment.getId(),
                apartment.getPrice(),
                apartment.getDisposition(),
                apartment.getEquipments().stream().map(Equipment::getId).collect(Collectors.toList())
        );
    }

    private Optional<ApartmentDTO> toDTO(Optional<Apartment> apartment) {
        if (apartment.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(apartment.get()));
    }
}
