package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOfferDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Offer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IOrderService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IUserService;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

	private IOrderService orderService;
	private IUserService userService;

	@Autowired
	public OrderController(IOrderService orderService, IUserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Order save(@RequestBody NewOrderDTO newOrder) {
		return orderService.save(newOrder);
	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('DEALER')")
	public List<Order> getAll(){
		return orderService.getAllOrders();
	}
	
	@GetMapping(value = "/dealerOffers")
	@PreAuthorize("hasRole('DEALER')")
	public List<Offer> getAllByDealer(Authentication auth){
		Dealer d = (Dealer) userService.findById(((User) auth.getPrincipal()).getId());
		return orderService.findAllOffersByDealer(d);
	}
	
	@PostMapping(value = "/{id}/offer")
	@PreAuthorize("hasRole('DEALER')")
	public Offer createOffer(@PathVariable("id") Long orderId, Authentication auth, @RequestBody NewOfferDTO nodto) {
		return orderService.createOffer(orderId, (Dealer) userService.findById(((User) auth.getPrincipal()).getId()), nodto);
	}
}
