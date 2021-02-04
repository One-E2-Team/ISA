package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineWithQuantityDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineWithQuantityRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IOrderRepository;

@Service
public class OrderService implements IOrderService {

	private IOrderRepository orderRepository;
	private IMedicineRepository medicineRepository;
	private IMedicineWithQuantityRepository medicineWithQuantityRepository;

	@Autowired
	public OrderService(IOrderRepository orderRepository, IMedicineRepository medicineRepository,
			IMedicineWithQuantityRepository medicineWithQuantityRepository) {
		this.orderRepository = orderRepository;
		this.medicineRepository = medicineRepository;
		this.medicineWithQuantityRepository = medicineWithQuantityRepository;
	}

	@Override
	public Order save(NewOrderDTO newOrder) {
		Order order = new Order();
		order.setExpireDate(newOrder.getExpireDate());
		order.setMedicinesWithQuantity(new ArrayList<MedicineWithQuantity>());
		for (NewMedicineWithQuantityDTO newMedWithQuant : newOrder.getNewMedicineWithQuantity()) {
			MedicineWithQuantity medWithQuant = new MedicineWithQuantity();
			medWithQuant.setQuantity(newMedWithQuant.getQuantity());
			Medicine medicine = medicineRepository.findById(newMedWithQuant.getMedicineId()).orElse(null);
			if (medicine == null)
				return null;
			medWithQuant.setMedicine(medicine);
			medWithQuant = medicineWithQuantityRepository.save(medWithQuant);
			order.getMedicinesWithQuantity().add(medWithQuant);
		}
		return orderRepository.save(order);
	}

}
