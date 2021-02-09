package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;

public interface IOrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "select o from Order o where o.expireDate > CURRENT_TIMESTAMP and o.finished = false") //false -- if true its deleted
	List<Order> findAllActive();

	@Query(value = "select * from orders o where o.pharmacy_id = ?1 and o.finished = false", nativeQuery = true)
	List<Order> getAllByPharmacy(Long pharmacyId);
}
