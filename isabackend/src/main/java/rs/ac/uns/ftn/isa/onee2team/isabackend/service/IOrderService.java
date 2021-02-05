package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;

public interface IOrderService {
	Order save(NewOrderDTO newOrder);
}
