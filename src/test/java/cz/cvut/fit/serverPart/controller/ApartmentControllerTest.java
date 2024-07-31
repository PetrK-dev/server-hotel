package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.ApartmentCreateDTO;
import cz.cvut.fit.serverPart.dto.ApartmentDTO;
import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.entity.Reservation;
import cz.cvut.fit.serverPart.service.ApartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ApartmentControllerTest {

    @Autowired
    private ApartmentController apartmentController;

    @MockBean
    private ApartmentService apartmentService;

    @Test
    void readAll() {
        List<ApartmentDTO> apartmentsDTO = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        ApartmentDTO apartmentDTO0 = new ApartmentDTO(0,8500, "2+1", equipmentsIds);
        ApartmentDTO apartmentDTO1 = new ApartmentDTO(1,9800, "3+1", equipmentsIds);
        ApartmentDTO apartmentDTO2 = new ApartmentDTO(2,3500, "1+1", equipmentsIds);
        apartmentsDTO.add(apartmentDTO0);
        apartmentsDTO.add(apartmentDTO1);
        apartmentsDTO.add(apartmentDTO2);

        BDDMockito.given(apartmentService.findAll()).willReturn(apartmentsDTO);

        List<ApartmentDTO> apartmentsDTOList = apartmentController.readAll();
        Assertions.assertEquals(apartmentsDTOList.size(), apartmentsDTO.size());
        Assertions.assertEquals(apartmentsDTOList.get(0).getId(), apartmentsDTO.get(0).getId());
        Assertions.assertEquals(apartmentsDTOList.get(1).getId(), apartmentsDTO.get(1).getId());
        Assertions.assertEquals(apartmentsDTOList.get(2).getId(), apartmentsDTO.get(2).getId());
        Assertions.assertEquals(apartmentsDTOList.get(0).getPrice(), apartmentsDTO.get(0).getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(1).getPrice(), apartmentsDTO.get(1).getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(2).getPrice(), apartmentsDTO.get(2).getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(0).getDisposition(), apartmentsDTO.get(0).getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(1).getDisposition(), apartmentsDTO.get(1).getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(2).getDisposition(), apartmentsDTO.get(2).getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(0).getEquipmentsIDs(), apartmentsDTO.get(0).getEquipmentsIDs());
        Assertions.assertEquals(apartmentsDTOList.get(1).getEquipmentsIDs(), apartmentsDTO.get(1).getEquipmentsIDs());
        Assertions.assertEquals(apartmentsDTOList.get(2).getEquipmentsIDs(), apartmentsDTO.get(2).getEquipmentsIDs());

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void readOneById() {
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);

        ApartmentDTO apartmentDTO = new ApartmentDTO(0,9500, "2+1", equipmentsIds);

        BDDMockito.given(apartmentService.findByIdAsDTO(apartmentDTO.getId())).willReturn(Optional.of(apartmentDTO));

        Assertions.assertEquals(apartmentDTO, apartmentController.readOneById(apartment.getId()));

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).findByIdAsDTO(apartment.getId());
    }

    @Test
    void findAllWithDisposition(){
        String disposition = "2+1";
        List<ApartmentDTO> apartmentsDTO = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        ApartmentDTO apartmentDTO0 = new ApartmentDTO(0,3500, "1+1", equipmentsIds);
        ApartmentDTO apartmentDTO1 = new ApartmentDTO(1,8500, "2+1", equipmentsIds);
        ApartmentDTO apartmentDTO2 = new ApartmentDTO(2,9800, "3+1", equipmentsIds);
        apartmentsDTO.add(apartmentDTO1);

        BDDMockito.given(apartmentService.findAllWithDisposition(disposition)).willReturn(apartmentsDTO);

        List<ApartmentDTO> apartmentsDTOList = apartmentService.findAllWithDisposition(disposition);

        Assertions.assertEquals(apartmentsDTOList.size(), apartmentsDTO.size());
        Assertions.assertEquals(apartmentsDTOList.get(0).getId(), apartmentDTO1.getId());
        Assertions.assertEquals(apartmentsDTOList.get(0).getPrice(), apartmentDTO1.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(0).getDisposition(), apartmentDTO1.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(0).getEquipmentsIDs(), equipmentsIds);

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).findAllWithDisposition(disposition);
    }

    @Test
    void findAllWithClientsHistory(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        List<ApartmentDTO> apartments = new ArrayList<>();
        ApartmentDTO apartmentDTO0 = new ApartmentDTO(0,3500, "1+1", equipmentsIds);
        ApartmentDTO apartmentDTO1 = new ApartmentDTO(1,8500, "2+1", equipmentsIds);
        ApartmentDTO apartmentDTO2 = new ApartmentDTO(2,9800, "3+1", equipmentsIds);
        Apartment apartment0 = new Apartment(3500, "1+1",equipments);
        ReflectionTestUtils.setField(apartment0, "id", 0);
        Apartment apartment1 = new Apartment(8500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment1, "id", 1);
        Apartment apartment2 = new Apartment(9800, "3+1",equipments);
        ReflectionTestUtils.setField(apartment2, "id", 2);
        apartments.add(apartmentDTO0);
        apartments.add(apartmentDTO1);
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

        BDDMockito.given(apartmentService.findAllWithClientsHistory(client0.getId())).willReturn(apartments);

        List<ApartmentDTO> apartmentsDTOList = apartmentService.findAllWithClientsHistory(client0.getId());

        Assertions.assertEquals(apartmentsDTOList.size(), apartments.size());
        Assertions.assertEquals(apartmentsDTOList.get(0).getId(), apartmentDTO0.getId());
        Assertions.assertEquals(apartmentsDTOList.get(1).getId(), apartmentDTO1.getId());
        Assertions.assertEquals(apartmentsDTOList.get(0).getPrice(), apartmentDTO0.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(1).getPrice(), apartmentDTO1.getPrice());
        Assertions.assertEquals(apartmentsDTOList.get(0).getDisposition(), apartmentDTO0.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(1).getDisposition(), apartmentDTO1.getDisposition());
        Assertions.assertEquals(apartmentsDTOList.get(0).getEquipmentsIDs(), equipmentsIds);
        Assertions.assertEquals(apartmentsDTOList.get(1).getEquipmentsIDs(), equipmentsIds);

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).findAllWithClientsHistory(client0.getId());
    }

    @Test
    void create(){
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);
        ApartmentDTO apartmentDTO = new ApartmentDTO(0,9500, "2+1", equipmentsIds);
        ApartmentCreateDTO apartmentCreateDTO = new ApartmentCreateDTO(9500, "2+1", equipmentsIds);

        BDDMockito.given(apartmentService.create(apartmentCreateDTO)).willReturn(apartmentDTO);

        Assertions.assertEquals(apartmentDTO, apartmentController.create(apartmentCreateDTO));

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).create(apartmentCreateDTO);
    }

    @Test
    void update(){
        List<Equipment> equipments = new ArrayList<>();
        List<Integer> equipmentsIds = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);
        ApartmentCreateDTO apartmentCreateDTO = new ApartmentCreateDTO(9500, "2+1", equipmentsIds);
        ApartmentDTO apartmentDTO = new ApartmentDTO(apartment.getId(),12000, "3+1", equipmentsIds);

        BDDMockito.given(apartmentService.update(apartment.getId(), apartmentCreateDTO)).willReturn(apartmentDTO);

        Assertions.assertEquals(apartmentDTO, apartmentController.update(apartment.getId(), apartmentCreateDTO));

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).update(apartment.getId(), apartmentCreateDTO);
    }

    @Test
    void delete(){
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(9500, "2+1",equipments);
        ReflectionTestUtils.setField(apartment, "id", 0);

        BDDMockito.given(apartmentService.findById(apartment.getId())).willReturn(Optional.of(apartment));

        apartmentController.deleteById(apartment.getId());

        Mockito.verify(apartmentService, Mockito.atLeastOnce()).deleteById(apartment.getId());
    }
}