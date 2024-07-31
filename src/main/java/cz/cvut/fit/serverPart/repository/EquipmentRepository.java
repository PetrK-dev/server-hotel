package cz.cvut.fit.serverPart.repository;

import cz.cvut.fit.serverPart.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}
