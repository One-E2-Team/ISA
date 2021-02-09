package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineWithQuantityDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOfferDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewOrderDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.OfferDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Offer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.OfferStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement.Order;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineWithQuantityRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IOfferRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IOrderRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWarehouseRepository;

@Service
public class OrderService implements IOrderService {

	private IOrderRepository orderRepository;
	private IMedicineRepository medicineRepository;
	private IMedicineWithQuantityRepository medicineWithQuantityRepository;
	private IWarehouseRepository warehouseRepository;
	private IPharmacyRepository pharmacyRepository;
	private IOfferRepository offerRepository;
	private IUserRepository userRepository;
	private IEmailNotificationService emailService;

	@Autowired
	public OrderService(IOrderRepository orderRepository, IMedicineRepository medicineRepository,
			IMedicineWithQuantityRepository medicineWithQuantityRepository, IWarehouseRepository warehouseRepository,
			IPharmacyRepository pharmacyRepository, IOfferRepository offerRepository, IUserRepository userRepository,
			IEmailNotificationService emailService) {
		this.orderRepository = orderRepository;
		this.medicineRepository = medicineRepository;
		this.medicineWithQuantityRepository = medicineWithQuantityRepository;
		this.warehouseRepository = warehouseRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.offerRepository = offerRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
	}

	@Override
	public Order save(NewOrderDTO newOrder, Long creatorId) {
		Order order = new Order();
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(creatorId).orElse(null);
		if (admin == null)
			return null;
		order.setExpireDate(newOrder.getExpireDate());
		order.setCreatorId(creatorId);
		order.setPharmacyId(admin.getPharmacy().getId());
		order.setFinished(false);
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
			if (!medicinesIhPharmacy.contains(medicine.getId())) {
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

	@Override
	public List<Order> getAllActiveOrders() {
		return orderRepository.findAllActive();
	}

	@Override
	public List<Offer> findAllOffersByDealer(Dealer d) {
		return offerRepository.findAllByDealer(d);
	}

	@Override
	public Offer createOrUpdateOffer(Long orderId, Dealer dealer, NewOfferDTO nodto) {
		Order order = orderRepository.findById(orderId).get();
		if (order == null)
			return null;
		if (order.getExpireDate().compareTo(new Date()) <= 0)
			return null;
		List<Offer> offers = offerRepository.findAllByDealerAndOrder(dealer, order);
		Offer o;
		if (offers.size() != 0)
			o = offers.get(0);
		else {
			o = new Offer();
			o.setOrder(order);
			o.setDealer(dealer);
			o.setStatus(OfferStatus.CREATED);
		}
		o.setFullPrice(nodto.getFullPrice());
		o.setDate(nodto.getDate());
		for (MedicineWithQuantity mwq : o.getOrder().getMedicinesWithQuantity()) {
			boolean handled = false;
			for (MedicineWithQuantity dealermwq : o.getDealer().getMedicinesWithQuantity()) {
				if (mwq.getMedicine().getId().equals(dealermwq.getMedicine().getId()))
					if (mwq.getQuantity() > dealermwq.getQuantity())
						return null;
					else {
						handled = true;
						break;
					}
			}
			if (!handled)
				return null;
		}
		return offerRepository.saveAndFlush(o);
	}

	@Override
	public List<Order> getAllOrdersByPharmacy(Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if (admin == null)
			return null;
		return orderRepository.getAllByPharmacy(admin.getPharmacy().getId());
	}

	@Override
	public List<OfferDTO> getAllOffersByOrder(Long orderId) {
		List<OfferDTO> ret = new ArrayList<OfferDTO>();
		for (Offer offer : offerRepository.getAllOffersByOrder(orderId)) {
			OfferDTO dto = new OfferDTO(offer.getId(), offer.getFullPrice(), offer.getDate(),
					offer.getDealer().getFirstName());
			ret.add(dto);
		}
		return ret;
	}

	@Override
	public String acceptOffer(Long offerId, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		Offer accepting = offerRepository.findById(offerId).orElse(null);
		if(admin == null || accepting == null)
			return "Error";
		Order order = orderRepository.findById(accepting.getOrder().getId()).orElse(null);
		if (order == null)
			return "Error";
		if (admin.getId() != accepting.getOrder().getCreatorId())
			return "Only creator of order can accept offer!";
		if(order.getExpireDate().after(new Date())) 
			return "Can't accept offer before expire date!";
		if(order.getFinished())
			return "Order has already finished!";
		Dealer dealer = accepting.getDealer();

		List<Offer> allOffers = offerRepository.getAllOffersByOrder(accepting.getOrder().getId());
		accepting.setStatus(OfferStatus.ACCEPTED);
		offerRepository.save(accepting);
		String message = "Dear " + accepting.getDealer().getFirstName() + " " + dealer.getLastName()
				+ ", your offer has been accepted!";
		emailService.sendNotificationAsync(dealer.getEmail(), "Offer accepted", message);
		for (Offer o : allOffers) {
			if (o.getId() == accepting.getId())
				continue;
			o.setStatus(OfferStatus.DECLINED);
			message = "Dear " + o.getDealer().getFirstName() + " " + o.getDealer().getLastName()
					+ ", your offer has been declined!";
			emailService.sendNotificationAsync(o.getDealer().getEmail(), "Offer declined", message);
			offerRepository.save(o);
		}
		order.setFinished(true);
		List<MedicineWithQuantity> mwdsDealer = accepting.getDealer().getMedicinesWithQuantity();
		//List<MedicineWithQuantity> newDealerMwd = new ArrayList<MedicineWithQuantity>();
		for (MedicineWithQuantity mwdOrder : order.getMedicinesWithQuantity()) {
			for (MedicineWithQuantity mwdDealer : mwdsDealer) {
				if(mwdOrder.getMedicine().getId() == mwdDealer.getMedicine().getId()) {
					mwdDealer.setQuantity(mwdDealer.getQuantity() - mwdOrder.getQuantity());
					medicineWithQuantityRepository.save(mwdDealer);
					Warehouse w = warehouseRepository.getByMedicineAndPharmacy(mwdOrder.getMedicine().getId(), admin.getPharmacy().getId());
					w.setAmount(w.getAmount() + mwdOrder.getQuantity());
					warehouseRepository.save(w);
					//MedicineWithQuantity newMwd = new MedicineWithQuantity();
					//newMwd.setMedicine(mwdDealer.getMedicine());
					//newMwd.setQuantity(mwdDealer.getQuantity() - mwdOrder.getQuantity());
					//newDealerMwd.add(newMwd);
					//medicineWithQuantityRepository.save(newMwd);
				}
				//else
					//newDealerMwd.add(mwdDealer);
			}
		}
		dealer.setMedicinesWithQuantity(mwdsDealer);
		userRepository.save(dealer);
		
		orderRepository.save(order);
		return "Succesfully accepted offer!";
	}

	@Override
	public String deleteOrder(Long orderId, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		Order order = orderRepository.findById(orderId).orElse(null);
		if(admin == null || order == null)
			return "Error";
		if (admin.getId() != order.getCreatorId())
			return "Only creator of order can delete order!";
		List<Offer> offers = offerRepository.getAllOffersByOrder(orderId);
		if(offers == null || offers.size() == 0) {
			order.setFinished(true);
			orderRepository.save(order);
			return "Successfully deleted order!";
		}
		return "Can't delete order that has offers!";
	}

}
