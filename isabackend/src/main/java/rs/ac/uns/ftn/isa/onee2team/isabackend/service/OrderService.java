package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineWithQuantityDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineWithQuantityRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IOrderRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWarehouseRepository;

@Service
public class OrderService implements IOrderService {

	private IOrderRepository orderRepository;
	private IMedicineRepository medicineRepository;
	private IMedicineWithQuantityRepository medicineWithQuantityRepository;
	private IWarehouseRepository warehouseRepository;
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	public OrderService(IOrderRepository orderRepository, IMedicineRepository medicineRepository,
			IMedicineWithQuantityRepository medicineWithQuantityRepository, IWarehouseRepository warehouseRepository, IPharmacyRepository pharmacyRepository) {
		this.orderRepository = orderRepository;
		this.medicineRepository = medicineRepository;
		this.medicineWithQuantityRepository = medicineWithQuantityRepository;
		this.warehouseRepository = warehouseRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public Order save(NewOrderDTO newOrder) {
		Order order = new Order();
		order.setExpireDate(newOrder.getExpireDate());
		order.setMedicinesWithQuantity(new ArrayList<MedicineWithQuantity>());
		List<Long> medicinesIhPharmacy = warehouseRepository.getAllMedicinesInPharmacy(newOrder.getPharmacyId());
		for (NewMedicineWithQuantityDTO newMedWithQuant : newOrder.getNewMedicineWithQuantity()) {
			MedicineWithQuantity medWithQuant = new MedicineWithQuantity();
			medWithQuant.setQuantity(newMedWithQuant.getQuantity());
			Medicine medicine = medicineRepository.findById(newMedWithQuant.getMedicineId()).orElse(null);
			if (medicine == null) {
				return null;
			}
			medWithQuant.setMedicine(medicine);
			medWithQuant = medicineWithQuantityRepository.save(medWithQuant);
			if(!medicinesIhPharmacy.contains(medicine.getId())) {
				Warehouse warehouse = new Warehouse();
				warehouse.setAmount(0);
				warehouse.setReservedAmount(0);
				warehouse.setMedicine(medicine);
				warehouse.setPharmacy(pharmacyRepository.findById(newOrder.getPharmacyId()).orElse(null));
				warehouseRepository.save(warehouse);
			}
			order.getMedicinesWithQuantity().add(medWithQuant);
		}
		return orderRepository.save(order);
	}

}
