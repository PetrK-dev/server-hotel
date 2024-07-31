package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.ReservationCreateDTO;
import cz.cvut.fit.serverPart.dto.ReservationDTO;
import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.entity.Reservation;
import cz.cvut.fit.serverPart.service.ReservationService;
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
class ReservationControllerTest {

    @Autowired
    private ReservationController reservationController;

    @MockBean
    private ReservationService reservationService;

    @Test
    void readAll() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date3 = new Date(2020-3-1);
        Date date4 = new Date(2020-4-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        ReservationDTO reservation0 = new ReservationDTO(0,date1, date2,client.getId(), apartment.getId());
        ReservationDTO reservation1 = new ReservationDTO(1,date2, date3,client.getId(), apartment.getId());
        ReservationDTO reservation2 = new ReservationDTO(2,date3, date4,client.getId(), apartment.getId());
        reservationsDTO.add(reservation0);
        reservationsDTO.add(reservation1);
        reservationsDTO.add(reservation2);

        BDDMockito.given(reservationService.findAll()).willReturn(reservationsDTO);

        List<ReservationDTO> reservationsDTOList = reservationService.findAll();

        Assertions.assertEquals(reservationsDTOList.size(), reservationsDTO.size());
        Assertions.assertEquals(reservationsDTOList.get(0).getId(), reservation0.getId());
        Assertions.assertEquals(reservationsDTOList.get(1).getId(), reservation1.getId());
        Assertions.assertEquals(reservationsDTOList.get(2).getId(), reservation2.getId());
        Assertions.assertEquals(reservationsDTOList.get(0).getFrom(), reservation0.getFrom());
        Assertions.assertEquals(reservationsDTOList.get(1).getFrom(), reservation1.getFrom());
        Assertions.assertEquals(reservationsDTOList.get(2).getFrom(), reservation2.getFrom());
        Assertions.assertEquals(reservationsDTOList.get(0).getTo(), reservation0.getTo());
        Assertions.assertEquals(reservationsDTOList.get(1).getTo(), reservation1.getTo());
        Assertions.assertEquals(reservationsDTOList.get(2).getTo(), reservation2.getTo());
        Assertions.assertEquals(reservationsDTOList.get(0).getClientOfReservationId(), client.getId());
        Assertions.assertEquals(reservationsDTOList.get(1).getClientOfReservationId(), client.getId());
        Assertions.assertEquals(reservationsDTOList.get(2).getClientOfReservationId(), client.getId());
        Assertions.assertEquals(reservationsDTOList.get(0).getApartmentOfReservationId(), apartment.getId());
        Assertions.assertEquals(reservationsDTOList.get(1).getApartmentOfReservationId(), apartment.getId());
        Assertions.assertEquals(reservationsDTOList.get(2).getApartmentOfReservationId(), apartment.getId());

        Mockito.verify(reservationService, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void readOneById() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);
        ReservationDTO reservationDTO = new ReservationDTO(reservation.getId(),reservation.getFrom(),reservation.getTo(),
                            reservation.getClientOfReservation().getId(),reservation.getApartmentOfReservation().getId());

        BDDMockito.given(reservationService.findByIdAsDTO(reservationDTO.getId())).willReturn(Optional.of(reservationDTO));

        Assertions.assertEquals(reservationDTO, reservationController.readOneById(reservation.getId()));

        Mockito.verify(reservationService, Mockito.atLeastOnce()).findByIdAsDTO(reservation.getId());
    }

    @Test
    void create(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);
        ReservationDTO reservationDTO = new ReservationDTO(0,date1, date2,client.getId(), apartment.getId());
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO(date1, date2,client.getId(), apartment.getId());

        BDDMockito.given(reservationService.create(reservationCreateDTO)).willReturn(reservationDTO);

        Assertions.assertEquals(reservationDTO, reservationController.create(reservationCreateDTO));

        Mockito.verify(reservationService, Mockito.atLeastOnce()).create(reservationCreateDTO);
    }

    @Test
    void update(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date3 = new Date(2020-3-1);
        Date date8 = new Date(2020-4-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO(date3, date8,client.getId(), apartment.getId());
        ReservationDTO reservationDTO = new ReservationDTO(0,date3, date8,client.getId(), apartment.getId());

        BDDMockito.given(reservationService.update(reservation.getId(), reservationCreateDTO)).willReturn(reservationDTO);

        Assertions.assertEquals(reservationDTO, reservationController.update(reservation.getId(), reservationCreateDTO));

        Mockito.verify(reservationService, Mockito.atLeastOnce()).update(reservation.getId(), reservationCreateDTO);
    }

    @Test
    void delete(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);

        BDDMockito.given(reservationService.findById(reservation.getId())).willReturn(Optional.of(reservation));

        reservationController.deleteById(reservation.getId());

        Mockito.verify(reservationService, Mockito.atLeastOnce()).deleteById(reservation.getId());
    }
}