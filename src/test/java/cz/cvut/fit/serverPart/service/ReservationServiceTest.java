package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.ReservationCreateDTO;
import cz.cvut.fit.serverPart.dto.ReservationDTO;
import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.entity.Reservation;
import cz.cvut.fit.serverPart.entity.Equipment;
import cz.cvut.fit.serverPart.repository.ApartmentRepository;
import cz.cvut.fit.serverPart.repository.ClientRepository;
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
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private ApartmentRepository apartmentRepository;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    void findAll() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date3 = new Date(2020-3-1);
        Date date4 = new Date(2020-4-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation0 = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation0, "id", 0);
        Reservation reservation1 = new Reservation(date2, date3,client, apartment);
        ReflectionTestUtils.setField(reservation1, "id", 1);
        Reservation reservation2 = new Reservation(date3, date4,client, apartment);
        ReflectionTestUtils.setField(reservation2, "id", 2);
        reservations.add(reservation0);
        reservations.add(reservation1);
        reservations.add(reservation2);


        BDDMockito.given(reservationRepository.findAll()).willReturn(reservations);

        List<ReservationDTO> reservationsDTOList = reservationService.findAll();

        Assertions.assertEquals(reservationsDTOList.size(), reservations.size());
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

        Mockito.verify(reservationRepository, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void findByIds() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date3 = new Date(2020-3-1);
        Date date4 = new Date(2020-4-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        List<Reservation> reservations = new ArrayList<>();
        List<Integer> reservationsIds = new ArrayList<>();
        Reservation reservation0 = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation0, "id", 0);
        Reservation reservation1 = new Reservation(date2, date3,client, apartment);
        ReflectionTestUtils.setField(reservation1, "id", 1);
        Reservation reservation2 = new Reservation(date3, date4,client, apartment);
        ReflectionTestUtils.setField(reservation2, "id", 2);
        reservationsIds.add(reservation0.getId());
        reservationsIds.add(reservation1.getId());
        reservationsIds.add(reservation2.getId());
        reservations.add(reservation0);
        reservations.add(reservation1);
        reservations.add(reservation2);

        BDDMockito.given(reservationRepository.findAllById(reservationsIds)).willReturn(reservations);

        List<Reservation> reservationsList = reservationService.findByIds(reservationsIds);

        Assertions.assertEquals(reservationsList.size(), reservations.size());
        Assertions.assertEquals(reservationsList.get(0), reservation0);
        Assertions.assertEquals(reservationsList.get(1), reservation1);
        Assertions.assertEquals(reservationsList.get(2), reservation2);

        Mockito.verify(reservationRepository, Mockito.atLeastOnce()).findAllById(reservationsIds);
    }

    @Test
    void findById() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);

        BDDMockito.given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));
        Optional<Reservation> reservationOptional = reservationService.findById(reservation.getId());

        if(reservationOptional.isPresent()) {
            Reservation reservation1 = reservationOptional.get();
            Assertions.assertEquals(reservation, reservation1);
        }
        else
            throw new EntityNotFoundException("Reservation service returned null");

        Mockito.verify(reservationRepository,Mockito.atLeastOnce()).findById(reservation.getId());
    }

    @Test
    void findByIdAsDTO() {
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);
        ReservationDTO reservationDTO = new ReservationDTO(reservation.getId(), date1, date2, client.getId(), apartment.getId());

        BDDMockito.given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));

        if(reservationService.findByIdAsDTO(reservation.getId()).isPresent()) {
            ReservationDTO reservationDTO1 = reservationService.findByIdAsDTO(reservation.getId()).get();
            Assertions.assertEquals(reservationDTO, reservationDTO1);
        }
        else
            throw new EntityNotFoundException("Reservation service returned null");

        Mockito.verify(reservationRepository,Mockito.atLeastOnce()).findById(reservation.getId());
    }

    @Test
    void create(){
        List<Equipment> equipments = new ArrayList<>();
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);
        ReservationDTO reservationDTO = new ReservationDTO(reservation.getId(),date1, date2,
                client.getId(), apartment.getId());
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO(date1, date2, client.getId(), apartment.getId());

        BDDMockito.given(apartmentRepository.findById(apartment.getId())).willReturn(Optional.of(apartment));
        BDDMockito.given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));
        BDDMockito.given(reservationRepository.save((Reservation)notNull())).willReturn(reservation);
        try {
            Assertions.assertEquals(reservationDTO, reservationService.create(reservationCreateDTO));
        }catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("Client or Apartment didnt return");
        }

        Mockito.verify(apartmentRepository, Mockito.atLeastOnce()).findById(apartment.getId());
        Mockito.verify(clientRepository, Mockito.atLeastOnce()).findById(client.getId());
        Mockito.verify(reservationRepository, Mockito.atLeastOnce()).save((Reservation)notNull());
    }

    @Test
    void update(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        Date date3 = new Date(2020-3-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);
        ReservationCreateDTO reservationCreateDTOUpdate = new ReservationCreateDTO(date2, date3, client.getId(), apartment.getId());
        BDDMockito.given(apartmentRepository.findById(apartment.getId())).willReturn(Optional.of(apartment));
        BDDMockito.given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));
        BDDMockito.given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));

        ReservationDTO reservationDTO;
        try {
            reservationDTO = reservationService.update(reservation.getId(), reservationCreateDTOUpdate);
        }catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("Reservation service returned Exception");
        }

        Assertions.assertEquals(reservationDTO.getId(), reservation.getId());
        Assertions.assertEquals(reservationDTO.getFrom(), reservation.getFrom());
        Assertions.assertEquals(reservationDTO.getTo(), reservation.getTo());
        Assertions.assertEquals(reservationDTO.getClientOfReservationId(), client.getId());
        Assertions.assertEquals(reservationDTO.getApartmentOfReservationId(), apartment.getId());

        Mockito.verify(reservationRepository,Mockito.atLeastOnce()).findById(reservation.getId());
    }

    @Test
    void deleteById(){
        Date date1 = new Date(2020-1-1);
        Date date2 = new Date(2020-2-1);
        List<Equipment> equipments = new ArrayList<>();
        Apartment apartment = new Apartment(8500, "2+1", equipments);
        Client client = new Client("Mr", "Nobody");
        Reservation reservation = new Reservation(date1, date2,client, apartment);
        ReflectionTestUtils.setField(reservation, "id", 0);

        BDDMockito.given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));

        try {
            reservationService.deleteById(0);
        }
        catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Reservation service returned Exception");
        }
        Mockito.verify(reservationRepository,Mockito.atLeastOnce()).findById(reservation.getId());
        Mockito.verify(reservationRepository, Mockito.atLeastOnce()).delete((Reservation)notNull());
    }
}