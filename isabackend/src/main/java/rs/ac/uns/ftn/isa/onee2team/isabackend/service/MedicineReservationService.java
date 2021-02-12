package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestReservationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ReservedMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineReservationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWarehouseRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWorkingCalendarRepository;

@Service
public class MedicineReservationService implements IMedicineReservationService {

	private IMedicineReservationRepository medicineReservationRepository;
	private IWarehouseRepository warehouseRepository;
	private IMedicineRepository medicineRepository;
	private IPharmacyRepository pharmacyRepository;
	private IUserRepository userRepository;
	private IEmailNotificationService emailNotificationService;
	
	@Autowired
	public MedicineReservationService(IMedicineReservationRepository medicineReservationRepository,
			IWarehouseRepository warehouseRepository, IMedicineRepository medicineRepository,
			IPharmacyRepository pharmacyRepository, IUserRepository userRepository,
			IEmailNotificationService emailNotificationService) {
		this.medicineReservationRepository = medicineReservationRepository;
		this.warehouseRepository = warehouseRepository;
		this.medicineRepository = medicineRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.userRepository = userRepository;
		this.emailNotificationService = emailNotificationService;
	}
	
	@Override
	public List<MedicineReservation> findAllDoneReservationsByPatient(Long patientId) {
		return medicineReservationRepository.getDoneReservationsByPatient(patientId);
	}
	
	@Override
	public List<ReservedMedicineDTO> getPatientsReservations(Long patient_id) {
		List<MedicineReservation> mr_list = medicineReservationRepository.getPatientsReservations(patient_id);
		List<ReservedMedicineDTO> ret_list = new ArrayList<ReservedMedicineDTO>();
		
		for(MedicineReservation mr : mr_list){
			ReservedMedicineDTO dto = new ReservedMedicineDTO();
			dto.setId(mr.getId());
			dto.setExpireDate(mr.getExpireDate());
			dto.setMedicineId(mr.getMedicine().getId());
			dto.setMedicineName(mr.getMedicine().getName());
			dto.setPharmacyId(mr.getPharmacy().getId());
			dto.setPharmacyName(mr.getPharmacy().getName());
			ret_list.add(dto);
		}
		
		return ret_list;
	}

	@Override
	@Transactional
	public boolean cancelReservation(Long user_id, Long reservation_id) {
		MedicineReservation r = medicineReservationRepository.findById(reservation_id).orElse(null);

		if(r.getPatient().getId() != user_id)
			return false;
		
		if(r.getStatus() == MedicineReservationStatus.CANCELED) {return false;}
		
		r.setStatus(MedicineReservationStatus.CANCELED);
		medicineReservationRepository.save(r);
		
		Warehouse w = warehouseRepository.getWarehouseByPharmacyAndMedicine(r.getPharmacy().getId(), r.getMedicine().getId());
		
		w.setReservedAmount(w.getReservedAmount() - 1);
		warehouseRepository.save(w);
		
		return true;
	}

	@Override
	@Transactional()
	public boolean reserve(RequestReservationDTO dto, Long patient_id) {
		
		if(userRepository.getPatientsPenalties(patient_id) >= 3) 
			return false;
		
		Warehouse w = warehouseRepository.getWarehouseByPharmacyAndMedicine(dto.getPharmacy_id(), dto.getMedicine_id());
		
		if(w.getAmount() == w.getReservedAmount())
			return false;
		
		w.setReservedAmount(w.getReservedAmount() + 1);
		warehouseRepository.save(w);
		
		MedicineReservation mr = new MedicineReservation();
		mr.setExpireDate(dto.getExpireDate());
		mr.setStatus(MedicineReservationStatus.CREATED);
		mr.setMedicine(medicineRepository.findById(dto.getMedicine_id()).orElse(null));
		mr.setPatient((Patient)userRepository.findById(patient_id).orElse(null));
		mr.setPharmacy(pharmacyRepository.findById(dto.getPharmacy_id()).orElse(null));
		medicineReservationRepository.save(mr);
		
		emailNotificationService.sendNotificationAsync(
				userRepository.findById(patient_id).orElse(null).getEmail(), 
				"Reservation number: " + mr.getId() , "You have successfully reserved a medicine!");
		
		return true;
	}

	@Override
	public List<NewRateDTO> getMedicinesForRate(Long patientId) {
		List<MedicineReservation> reservations = medicineReservationRepository.getDoneReservationsByPatient(patientId);
		List<NewRateDTO> ret_list = new ArrayList<NewRateDTO>();
		NewRateDTO dto;
		
		for(MedicineReservation mr : reservations) {
			dto = new NewRateDTO();
			dto.setRateEntityId(mr.getMedicine().getId());
			dto.setRateEntityName(mr.getMedicine().getName());
			dto.setRateEntityType("MEDICINE");
			dto.setRate(-1);
			
			if(!ret_list.contains(dto))
				ret_list.add(dto);
		}
		
		List<Medicine> medicines = medicineRepository.getMedicinesFromErecipesByPatient(patientId);
		for(Medicine m : medicines) {
			dto = new NewRateDTO();
			dto.setRateEntityId(m.getId());
			dto.setRateEntityName(m.getName());
			dto.setRateEntityType("MEDICINE");
			dto.setRate(-1);
			
			if(!ret_list.contains(dto)) { ret_list.add(dto); }
		}
		
		return ret_list;
	}

	@Override
	@Transactional
	public void takeMedicine(Long patientId, ReservedMedicineDTO dto) {
		
		MedicineReservation mr = medicineReservationRepository.findById(dto.getId()).orElse(null);
		
		if(mr.getStatus().equals(MedicineReservationStatus.DONE)) { return; }
		
		mr.setStatus(MedicineReservationStatus.DONE);
		medicineReservationRepository.save(mr);
		Warehouse w = warehouseRepository.getWarehouseByPharmacyAndMedicine(dto.getPharmacyId(), dto.getMedicineId());
		w.setAmount(w.getAmount() - 1);
		w.setReservedAmount(w.getReservedAmount() -1);
		warehouseRepository.save(w);
		
		Patient p = (Patient) userRepository.findById(patientId).orElse(null);
		p.setPoints(p.getPoints() + medicineRepository.findById(dto.getMedicineId()).orElse(null).getPoints());
		userRepository.save(p);
	}

	@Override
	public List<MedicineReservation> getAllMedicineReservations() {
		return medicineReservationRepository.findAll();
	}

	@Override
	public Boolean takeReservationMedicine(Long reservationId,Long healthworkerId) {
		MedicineReservation reservation = medicineReservationRepository.findById(reservationId).orElse(null);
		if(pharmacyRepository.getIfHealthWorkerWorksInPharmacy(healthworkerId, reservation.getPharmacy().getId())==0) {
			return false;
		}
		if(reservation == null || !reservation.getStatus().equals(MedicineReservationStatus.CREATED)) {
			return false;
		}
		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(reservation.getExpireDate());
	    calendar.add(Calendar.DATE, -1);
	    Date dayBefore = calendar.getTime();
		
		if(new Date().after(dayBefore)) {
			return false;
		}
		
		ReservedMedicineDTO resMedDTO = new ReservedMedicineDTO();
		resMedDTO.setId(reservation.getId());
		resMedDTO.setPharmacyId(reservation.getPharmacy().getId());
		resMedDTO.setMedicineId(reservation.getMedicine().getId());
		
		takeMedicine(reservation.getPatient().getId(), resMedDTO );
		return true;
	}

}
