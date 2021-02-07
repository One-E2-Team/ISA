package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOfferDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Offer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;

public interface IOrderService {
	Order save(NewOrderDTO newOrder);

	List<Order> getAllActiveOrders();

	List<Offer> findAllOffersByDealer(Dealer d);

	Offer createOrUpdateOffer(Long orderId, Dealer dealer, NewOfferDTO nodto);
}
