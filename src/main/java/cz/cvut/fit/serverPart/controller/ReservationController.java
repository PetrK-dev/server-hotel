package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.ReservationDTO;
import cz.cvut.fit.serverPart.dto.ReservationCreateDTO;
import cz.cvut.fit.serverPart.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public List<ReservationDTO> readAll() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public ReservationDTO readOneById(@PathVariable int id) {
        Optional<ReservationDTO> reservationDTOOptional = reservationService.findByIdAsDTO(id);
        if( reservationDTOOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ReservationDTO reservationDTO = reservationDTOOptional.get();
        reservationDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readOneById(id)).withRel("Reservation"));
        return reservationDTO;
    }

    @PostMapping
    public ReservationDTO create(@RequestBody ReservationCreateDTO reservation){
        try {
            ReservationDTO reservationDTO = reservationService.create(reservation);
            reservationDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).create(reservation)).withRel("Reservation"));
            return reservationDTO;
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ReservationDTO update(@PathVariable int id, @RequestBody ReservationCreateDTO reservation){
        try {
            ReservationDTO reservationDTO = reservationService.update(id, reservation);
            reservationDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).update(id,reservation)).withRel("Reservation"));
            return reservationDTO;
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        try {
            reservationService.deleteById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
