package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.ReservationCreateDTO;
import cz.cvut.fit.serverPart.dto.ReservationDTO;
import cz.cvut.fit.serverPart.entity.Apartment;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.entity.Reservation;
import cz.cvut.fit.serverPart.repository.ApartmentRepository;
import cz.cvut.fit.serverPart.repository.ClientRepository;
import cz.cvut.fit.serverPart.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository, ApartmentRepository apartmentRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll()  {
        return reservationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByIds(List<Integer> ids) {
        return reservationRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> findById(int id) {
        return reservationRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ReservationDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }

    @Transactional
    public ReservationDTO create(ReservationCreateDTO reservationDTO) throws EntityNotFoundException {
        Client reservationOfClient = clientRepository.findById( reservationDTO.getClientOfReservationId()).orElseThrow(() -> new EntityNotFoundException("no such client"));
        Apartment reservationOfApartment = apartmentRepository.findById( reservationDTO.getApartmentOfReservationId()).orElseThrow(() -> new EntityNotFoundException("no such apartment"));

        Reservation reservation = new Reservation(reservationDTO.getFrom(),reservationDTO.getTo(),reservationOfClient, reservationOfApartment);

        return toDTO(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationDTO update(int id, ReservationCreateDTO reservationDTO) throws EntityNotFoundException {
        Optional<Reservation> optionalReservation = findById(id);
        if (optionalReservation.isEmpty())
            throw new EntityNotFoundException("no such reservation");
        Reservation reservation = optionalReservation.get();
        reservation.setFrom(reservationDTO.getFrom());
        reservation.setTo(reservationDTO.getTo());
        reservation.setClientOfReservation(clientRepository.findById(reservationDTO.getClientOfReservationId())
                        .orElseThrow(() -> new EntityNotFoundException("no such client")) );
        reservation.setApartmentOfReservation(apartmentRepository.findById(reservationDTO.getApartmentOfReservationId())
                        .orElseThrow(() -> new EntityNotFoundException("no such apartment")));
        return toDTO(reservation);
    }

    @Transactional
    public void deleteById( int id)throws EntityNotFoundException{
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty())
            throw new EntityNotFoundException("no such reservation");
        Reservation reservation = optionalReservation.get();
        reservationRepository.delete(reservation);
    }

    private ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(),
                                 reservation.getFrom(),
                                 reservation.getTo(),
                                 reservation.getClientOfReservation().getId(),
                                 reservation.getApartmentOfReservation().getId() );
    }

    private Optional<ReservationDTO> toDTO(Optional<Reservation> reservation) {
        if (reservation.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(reservation.get()));
    }
}
