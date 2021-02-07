package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;

public interface IOrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "select o from Order o where o.expireDate > CURRENT_TIMESTAMP")
	List<Order> findAllActive();
}
