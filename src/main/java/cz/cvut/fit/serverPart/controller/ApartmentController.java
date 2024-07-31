package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.ApartmentCreateDTO;
import cz.cvut.fit.serverPart.dto.ApartmentDTO;
import cz.cvut.fit.serverPart.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.yaml.snakeyaml.util.UriEncoder.decode;


@RestController
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping("/all")
    public List<ApartmentDTO> readAll() {
        return apartmentService.findAll();
    }

    @GetMapping("/{id}")
    public ApartmentDTO readOneById(@PathVariable int id) {
        Optional<ApartmentDTO> apartmentDTOOptional = apartmentService.findByIdAsDTO(id);
        if( apartmentDTOOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ApartmentDTO apartmentDTO = apartmentDTOOptional.get();
        apartmentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readOneById(id)).withRel("Apartment"));
        return apartmentDTO;
    }

    @GetMapping( params = {"disposition"})
    public List<ApartmentDTO> readAllWithDisposition(@RequestParam String disposition){
        if(disposition.contains("+"))
            return apartmentService.findAllWithDisposition(disposition);
        return apartmentService.findAllWithDisposition(decode(disposition));
    }

    @GetMapping( params = {"client"})
    public List<ApartmentDTO> findAllWithClientsHistory(@RequestParam int client){
        return apartmentService.findAllWithClientsHistory(client);
    }

    @PostMapping
    public ApartmentDTO create(@RequestBody ApartmentCreateDTO apartment){
        try {
            ApartmentDTO apartmentDTO = apartmentService.create(apartment);
            apartmentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).create(apartment)).withRel("Apartment"));
            return apartmentDTO;
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ApartmentDTO update(@PathVariable int id, @RequestBody ApartmentCreateDTO apartment){
        try {
            ApartmentDTO apartmentDTO = apartmentService.update(id, apartment);
            apartmentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).update(id,apartment)).withRel("Apartment"));
            return apartmentDTO;
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        try {
            apartmentService.deleteById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
