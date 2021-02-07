package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Offer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;

public interface IOfferRepository extends JpaRepository<Offer, Long> {
	List<Offer> findAllByDealer(Dealer d);
	List<Offer> findAllByDealerAndOrder(Dealer dealer, Order order); 
}
