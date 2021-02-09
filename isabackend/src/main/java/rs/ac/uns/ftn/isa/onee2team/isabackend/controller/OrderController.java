package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOfferDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.OfferDTO;
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
	public Order save(@RequestBody NewOrderDTO newOrder, Authentication auth) {
		User user = (User) auth.getPrincipal();
		return orderService.save(newOrder, user.getId());
	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('DEALER')")
	public List<Order> getAll(){
		return orderService.getAllActiveOrders();
	}
	
	@GetMapping(value = "/dealerOffers")
	@PreAuthorize("hasRole('DEALER')")
	public List<Offer> getAllByDealer(Authentication auth){
		Dealer d = (Dealer) userService.findById(((User) auth.getPrincipal()).getId());
		return orderService.findAllOffersByDealer(d);
	}
	
	@PostMapping(value = "/{id}/offer")
	@PreAuthorize("hasRole('DEALER')")
	public Offer createOrUpdateOffer(@PathVariable("id") Long orderId, Authentication auth, @RequestBody NewOfferDTO nodto) {
		return orderService.createOrUpdateOffer(orderId, (Dealer) userService.findById(((User) auth.getPrincipal()).getId()), nodto);
	}
	
	@GetMapping(value = "/all-pharmacy")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<Order> getAllOrdersByPharmacy(Authentication auth){
		User user = (User) auth.getPrincipal();
		return orderService.getAllOrdersByPharmacy(user.getId());
	}
	
	@GetMapping(value = "/offer-by-order")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public List<OfferDTO> getAllOffersByOrder(@RequestParam Long orderId){
		return orderService.getAllOffersByOrder(orderId);
	}
	
	@PutMapping(value = "/accept")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<String> acceptOffer(@RequestParam Long offerId, Authentication auth) {
		User user = (User) auth.getPrincipal();
		String ret = orderService.acceptOffer(offerId, user.getId());
		if(!ret.equals("Succesfully accepted offer!")) {
			return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<String> deleteOrder(@RequestParam Long orderId, Authentication auth) {
		User user = (User) auth.getPrincipal();
		String ret = orderService.deleteOrder(orderId, user.getId());
		if(!ret.equals("Successfully deleted order!"))
			return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
}
