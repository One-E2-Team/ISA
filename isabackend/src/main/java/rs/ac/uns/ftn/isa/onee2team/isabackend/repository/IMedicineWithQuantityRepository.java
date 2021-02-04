package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;

public interface IMedicineWithQuantityRepository extends JpaRepository<MedicineWithQuantity, Long> {

}
