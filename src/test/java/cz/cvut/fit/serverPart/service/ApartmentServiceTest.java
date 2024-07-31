package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.ApartmentCreateDTO;
import cz.cvut.fit.serverPart.dto.ApartmentDTO;
import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.entity.Reservation;
import cz.cvut.fit.serverPart.repository.ApartmentRepository;
import cz.cvut.fit.serverPart.repository.EquipmentRepository;
import cz.cvut.fit.serverPart.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;

@SpringBootTest
class ApartmentServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @MockBean
    private ApartmentRepository apartmentRepository;

    @MockBean
    private EquipmentRepository equipmentRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    void findAll() {
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        Apartment apartment0 = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment0, "id", 0);
        Apartment apartment1 = new Apartment(12000, "3+1",equipments);
        ReflectionTestUtils.setField(apartment1, "id", 1);
        Apartment apartment2 = new Apartment(8000, "2+1",equipments);
        ReflectionTestUtils.setField(apartment2, "id", 2);
        apartments.add(apartment0);
        apartments.add(apartment1);
        apartments.add(apartment2);

        BDDMockito.given(apartmentRepository.findAll()).willReturn(apartments);

        List<ApartmentDTO> apartmentsDTOList = apartmentService.findAll();

        Assertions.assertEquals(apartmentsDTOList.size(), apartments.size());
        Assertions.assertEquals(apartmentsDTOList.get(0).getId(), apartment0.getId());
        Assertions.assertEquals(apartmentsDTOList.get(1).getId(), apartment1.getId());
        Assertions.assertEquals(apartmentsDTOList.get(2).getId(), apartment2.getId());
        Assertions.assertEquals(apartmentsDTOList.get(0).getPrice(), apartment0.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(1).getPrice(), apartment1.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(2).getPrice(), apartment2.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(0).getDisposition(), apartment0.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(1).getDisposition(), apartment1.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(2).getDisposition(), apartment2.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(0).getEquipmentsIDs(), equipmentsIds);
        Assertions.assertEquals(apartmentsDTOList.get(1).getEquipmentsIDs(), equipmentsIds);
        Assertions.assertEquals(apartmentsDTOList.get(2).getEquipmentsIDs(), equipmentsIds);

        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void findByIds() {
        List<Equipment> equipments = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        List<Integer> apartmentsIds = new ArrayList<>();
        Apartment apartment0 = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment0, "id", 0);
        Apartment apartment1 = new Apartment(12000, "3+1",equipments);
        ReflectionTestUtils.setField(apartment1, "id", 1);
        Apartment apartment2 = new Apartment(8000, "2+1",equipments);
        apartmentsIds.add(apartment0.getId());
        apartmentsIds.add(apartment1.getId());
        apartmentsIds.add(apartment2.getId());
        apartments.add(apartment0);
        apartments.add(apartment1);
        apartments.add(apartment2);

        BDDMockito.given(apartmentRepository.findAllById(apartmentsIds)).willReturn(apartments);

        List<Apartment> apartmentsList = apartmentService.findByIds(apartmentsIds);

        Assertions.assertEquals(apartmentsList.size(), apartments.size());
        Assertions.assertEquals(apartmentsList.get(0), apartment0);
        Assertions.assertEquals(apartmentsList.get(1), apartment1);
        Assertions.assertEquals(apartmentsList.get(2), apartment2);

        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).findAllById(apartmentsIds);
    }

    @Test
    void findById() {
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);

        BDDMockito.given(apartmentRepository.findById(apartment.getId())).willReturn(Optional.of(apartment));
        Optional<Apartment> apartmentOptional = apartmentService.findById(apartment.getId());

        if(apartmentOptional.isPresent()) {
            Apartment apartment1 = apartmentOptional.get();
            Assertions.assertEquals(apartment, apartment1);
        }
        else
            throw new EntityNotFoundException("Apartment service returned null");

        Mockito.verify(apartmentRepository,Mockito.atLeastOnce()).findById(apartment.getId());
    }

    @Test
    void findByIdAsDTO() {
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);
        ApartmentDTO apartmentDTO = new ApartmentDTO(apartment.getId(),9500, "2+1",equipmentsIds);

        BDDMockito.given(apartmentRepository.findById(apartment.getId())).willReturn(Optional.of(apartment));

        if(apartmentService.findByIdAsDTO(apartment.getId()).isPresent()) {
            ApartmentDTO apartmentDTO1 = apartmentService.findByIdAsDTO(apartment.getId()).get();
            Assertions.assertEquals(apartmentDTO, apartmentDTO1);
        }
        else
            throw new EntityNotFoundException("Apartment service returned null");

        Mockito.verify(apartmentRepository,Mockito.atLeastOnce()).findById(apartment.getId());
    }

    @Test
    void findAllWithDisposition(){
        String disposition = "2+1";
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        Apartment apartment0 = new Apartment(8000, "2+1",equipments);
        ReflectionTestUtils.setField(apartment0, "id", 0);
        Apartment apartment1 = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment1, "id", 1);
        Apartment apartment2 = new Apartment(12000, "3+1",equipments);
        ReflectionTestUtils.setField(apartment2, "id", 2);
        apartments.add(apartment0);
        apartments.add(apartment1);

        BDDMockito.given(apartmentRepository.findAllWithDisposition(disposition)).willReturn(apartments);

        List<ApartmentDTO> apartmentsDTOList = apartmentService.findAllWithDisposition(disposition);

        Assertions.assertEquals(apartmentsDTOList.size(), apartments.size());
        Assertions.assertEquals(apartmentsDTOList.get(0).getId(), apartment0.getId());
        Assertions.assertEquals(apartmentsDTOList.get(1).getId(), apartment1.getId());
        Assertions.assertEquals(apartmentsDTOList.get(0).getPrice(), apartment0.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(1).getPrice(), apartment1.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(0).getDisposition(), apartment0.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(1).getDisposition(), apartment1.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(0).getEquipmentsIDs(), equipmentsIds);
        Assertions.assertEquals(apartmentsDTOList.get(1).getEquipmentsIDs(), equipmentsIds);

        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).findAllWithDisposition(disposition);
    }

    @Test
    void findAllWithClientsHistory(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        Apartment apartment0 = new Apartment(8000, "2+1",equipments);
        ReflectionTestUtils.setField(apartment0, "id", 0);
        Apartment apartment1 = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment1, "id", 1);
        Apartment apartment2 = new Apartment(12000, "3+1",equipments);
        ReflectionTestUtils.setField(apartment2, "id", 2);
        apartments.add(apartment0);
        apartments.add(apartment1);
        Client client0 = new Client("Mr1", "Nobody1");
        ReflectionTestUtils.setField(client0, "id", 0);
        Client client1 = new Client("Mr2", "Nobody2");
        ReflectionTestUtils.setField(client1, "id", 1);
        Reservation reservation0 = new Reservation(date1, date2,client0, apartment0);
        ReflectionTestUtils.setField(reservation0, "id", 0);
        Reservation reservation1 = new Reservation(date1, date2,client0, apartment1);
        ReflectionTestUtils.setField(reservation1, "id", 1);
        Reservation reservation2 = new Reservation(date1, date2,client1, apartment2);
        ReflectionTestUtils.setField(reservation2, "id", 2);

        BDDMockito.given(apartmentRepository.findAllWithClientsHistory(client0.getId())).willReturn(apartments);

        List<ApartmentDTO> apartmentsDTOList = apartmentService.findAllWithClientsHistory(client0.getId());

        Assertions.assertEquals(apartmentsDTOList.size(), apartments.size());
        Assertions.assertEquals(apartmentsDTOList.get(0).getId(), apartment0.getId());
        Assertions.assertEquals(apartmentsDTOList.get(1).getId(), apartment1.getId());
        Assertions.assertEquals(apartmentsDTOList.get(0).getPrice(), apartment0.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(1).getPrice(), apartment1.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(0).getDisposition(), apartment0.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(1).getDisposition(), apartment1.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(0).getEquipmentsIDs(), equipmentsIds);
        Assertions.assertEquals(apartmentsDTOList.get(1).getEquipmentsIDs(), equipmentsIds);

        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).findAllWithClientsHistory(client0.getId());
    }

    @Test
    void create(){
        List<Equipment> equipments = new ArrayList<>();
        Equipment equipment = new Equipment("table");
        equipments.add(equipment);
        List<Integer> equipmentsIds = new ArrayList<>();
        equipmentsIds.add(equipment.getId());
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);
        ApartmentDTO apartmentDTO = new ApartmentDTO(apartment.getId(),
                                                    apartment.getPrice(),
                                                    apartment.getDisposition(),
                                                    equipmentsIds);
        ApartmentCreateDTO apartmentCreateDTO = new ApartmentCreateDTO(9500, "2+1",equipmentsIds);

        BDDMockito.given(equipmentRepository.findAllById(equipmentsIds)).willReturn(equipments);
        BDDMockito.given(apartmentRepository.save((Apartment)notNull())).willReturn(apartment);
        try {
            Assertions.assertEquals(apartmentDTO, apartmentService.create(apartmentCreateDTO));
        }catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("some equipments not found");
        }

        BDDMockito.given(equipmentRepository.findAllById(equipmentsIds)).willReturn(equipments);
        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).save((Apartment)notNull());
    }

    @Test
    void update(){
        List<Equipment> equipments = new ArrayList<>();
        Equipment equipment = new Equipment("table");
        equipments.add(equipment);
        List<Integer> equipmentsIds = new ArrayList<>();
        List<Integer> equipmentsIdsUpdate = new ArrayList<>();
        equipmentsIds.add(equipment.getId());
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);
        ApartmentCreateDTO apartmentCreateDTOUpdate = new ApartmentCreateDTO(3500, "2+1",equipmentsIdsUpdate);

        BDDMockito.given(apartmentRepository.findById(apartment.getId())).willReturn(Optional.of(apartment));

        ApartmentDTO apartmentDTO;
        try {
            apartmentDTO = apartmentService.update(apartment.getId(), apartmentCreateDTOUpdate);
        }catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("Apartment service returned Exception");
        }

        Assertions.assertEquals(apartmentDTO.getId(), apartment.getId());
        Assertions.assertEquals(apartmentDTO.getPrice(), apartment.getPrice());
        Assertions.assertEquals(apartmentDTO.getDisposition(), apartment.getDisposition());
        Assertions.assertEquals(apartmentDTO.getEquipmentsIDs(), equipmentsIdsUpdate);

        Mockito.verify(apartmentRepository,Mockito.atLeastOnce()).findById(apartment.getId());
    }

    @Test
    void deleteById(){
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);

        BDDMockito.given(apartmentRepository.findById(apartment.getId())).willReturn(Optional.of(apartment));

        try {
            apartmentService.deleteById(0);
        }
        catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Apartment service returned Exception");
        }
        Mockito.verify(apartmentRepository,Mockito.atLeastOnce()).findById(apartment.getId());
        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).delete((Apartment)notNull());
    }
}