package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.EquipmentDTO;
import cz.cvut.fit.serverPart.dto.EquipmentCreateDTO;
import cz.cvut.fit.serverPart.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/all")
    public List<EquipmentDTO> readAll() {
        return equipmentService.findAll();
    }

    @GetMapping("/{id}")
    public EquipmentDTO readOneById(@PathVariable int id) {
        Optional<EquipmentDTO> equipmentDTOOptional = equipmentService.findByIdAsDTO(id);
        if( equipmentDTOOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        EquipmentDTO equipmentDTO = equipmentDTOOptional.get();
        equipmentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readOneById(id)).withRel("Equipment"));
        return equipmentDTO;
    }

    @PostMapping
    public EquipmentDTO create(@RequestBody EquipmentCreateDTO equipment) {
        EquipmentDTO equipmentDTO = equipmentService.create(equipment);
        equipmentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).create(equipment)).withRel("Equipment"));
        return equipmentDTO;
    }

    @PutMapping("/{id}")
    public EquipmentDTO update(@PathVariable int id, @RequestBody EquipmentCreateDTO equipment){
        try {
            EquipmentDTO equipmentDTO = equipmentService.update(id, equipment);
            equipmentDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).update(id,equipment)).withRel("Equipment"));
            return equipmentDTO;
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        try {
            equipmentService.deleteById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
