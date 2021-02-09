package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;

public interface IMedicineWithQuantityRepository extends JpaRepository<MedicineWithQuantity, Long> {

	@Query(value = "select * from medicines_with_quantity mw where mw.id in "
			+ "(select omw.medicines_with_quantity_id from orders_medicines_with_quantity omw where omw.order_id in "
			+ "(select o.order_id from offers o where o.status = 1 and o.date >= ?2 and o.date <= ?3 "
			+ "and o.order_id in (select oor.id from orders oor where oor.pharmacy_id = ?1)))", nativeQuery = true)
	List<MedicineWithQuantity> medicinesBoughtByPharmacyInTimeInterval(Long pharmacyId, Date start, Date end);
}
