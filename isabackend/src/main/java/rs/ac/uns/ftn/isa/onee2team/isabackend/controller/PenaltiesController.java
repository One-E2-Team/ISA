package rs.ac.uns.ftn.isa.onee2team.isabackend.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineReservationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWarehouseRepository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

@Controller
public class PenaltiesController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private IMedicineReservationRepository medicineReservationRepository;
	private IWarehouseRepository warehouseRepository;
	private IUserRepository userRepository;
	
	@Autowired
	public PenaltiesController(IMedicineReservationRepository medicineReservationRepository,
			IWarehouseRepository warehouseRepository, IUserRepository userRepository) {
		this.medicineReservationRepository = medicineReservationRepository;
		this.warehouseRepository = warehouseRepository;
		this.userRepository = userRepository;
	}

	@Scheduled(initialDelay = 20000, fixedDelay = 3600000)
	public void checkForReservationsExpired() {
		logger.info("Started reservation checking..");
		
		List<MedicineReservation> reservations = medicineReservationRepository.findAll();
		
		for(MedicineReservation r : reservations) {
			if(new Date(System.currentTimeMillis()).after(r.getExpireDate()) 
					&& r.getStatus() == MedicineReservationStatus.CREATED){
				logger.info("Reservation " + r.getId() + " expired!");
				
				r.setStatus(MedicineReservationStatus.CANCELED);
				medicineReservationRepository.save(r);
				
				Warehouse w = warehouseRepository.getWarehouseByPharmacyAndMedicine(r.getPharmacy().getId(), r.getMedicine().getId());
				w.setReservedAmount(w.getReservedAmount() - 1);
				warehouseRepository.save(w);
				
				Patient patient = (Patient) userRepository.findById(r.getPatient().getId()).orElse(null);
				patient.setPenalties(patient.getPenalties() + 1);
				userRepository.save(patient);
				
				logger.info("Patient " + patient.getId() + " got a penalty!");
			}
		}
	}
	
	@Scheduled(cron = "00 00 00 01 * ?")
	public void resetPenalties() {
		
		logger.info("Reseting all users penalties..");
		
		List<User> users = userRepository.findAll();
		Patient p;
		for(User u : users) {
			if(u.getUserType() == UserType.PATIENT) {
				p = (Patient) u;
				p.setPenalties(0);
				userRepository.save(p);
			}
		}
		
		logger.info("Reseting finished.");
	}
	
}
