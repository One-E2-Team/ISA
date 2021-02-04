package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IOrderService;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

	private IOrderService orderService;

	@Autowired
	public OrderController(IOrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public Order save(@RequestBody NewOrderDTO newOrder) {
		return orderService.save(newOrder);
	}
}
