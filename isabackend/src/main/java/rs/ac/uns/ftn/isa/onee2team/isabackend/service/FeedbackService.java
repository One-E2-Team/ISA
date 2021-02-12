package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewComplaintDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.Complaint;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.ComplaintType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedHealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedPharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.ERecipe;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IComplaintRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IERecipeRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class FeedbackService implements IFeedbackService {

	private IComplaintRepository complaintRepository;
	private IEmailNotificationService emailNotificationService;
	private IExaminationService examinationService;
	private IMedicineReservationService medicineReservationService;
	private IPharmacyService pharmacyService;
	private IRatedEntitiesService ratedEntitiesService;
	private IMedicineRepository medicineRepository;
	private IUserRepository userRepository;
	private IERecipeRepository eRecipeRepository;

	@Autowired
	public FeedbackService(IComplaintRepository complaintRepository, IEmailNotificationService emailNotificationService, 
			IExaminationService examinationService, IMedicineReservationService medicineReservationService,
			IPharmacyService pharmacyService, IRatedEntitiesService ratedEntitiesService, IMedicineRepository medicineRepository,
			IUserRepository userRepository, IERecipeRepository eRecipeRepository) {
		super();
		this.complaintRepository = complaintRepository;
		this.emailNotificationService = emailNotificationService;
		this.examinationService = examinationService;
		this.medicineReservationService = medicineReservationService;
		this.pharmacyService = pharmacyService;
		this.ratedEntitiesService = ratedEntitiesService;
		this.medicineRepository = medicineRepository;
		this.userRepository = userRepository;
		this.eRecipeRepository = eRecipeRepository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Complaint saveComplaint(Complaint c) {
		return complaintRepository.saveAndFlush(c);
	}

	@Override
	@Transactional
	public Complaint answerComplaint(Long id, String answer) {
		Complaint c = complaintRepository.findById(id).orElse(null);
		if (!c.getHandled()) {
			c.setHandled(true);
			// should change with check for test profile, but this works too
			for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			    if (element.getClassName().startsWith("rs.ac.uns.ftn.isa.onee2team.isabackend.IsabackendApplicationTests"))
			    	try {
			    		Thread.sleep(5000);
			    	} catch (InterruptedException e) {
			    		e.printStackTrace();
			    	}
			}
			c.setAnswer(answer);
			c = this.saveComplaint(c);
			emailNotificationService.sendNotificationAsync(c.getPatient().getEmail(), "Answer to complaint number: " + c.getId().toString(), answer);
			return c;
		} else return null;
	}

	@Override
	public List<Complaint> getUnansweredComplaints() {
		return complaintRepository.findAllByHandled(false);
	}

	@Override
	public List<NewComplaintDTO> findPossibleComplaints(Patient patient) {
		List<NewComplaintDTO> ret = new ArrayList<NewComplaintDTO>();
		NewComplaintDTO temp;
		for (Examination e : examinationService.getExaminationsFromHistoryByPatientToDate(patient.getId())) {
			temp = new NewComplaintDTO(e.getHealthWokrer().getId(), e.getHealthWokrer().getFirstName() + " " + e.getHealthWokrer().getLastName(), e.getHealthWokrer().getUserType().equals(UserType.DERMATOLOGIST) ? "DERMATOLOGIST" : "PHARMACIST", "");
			if(!ret.contains(temp)) ret.add(temp);
			temp = new NewComplaintDTO(e.getPharmacy().getId(), e.getPharmacy().getName(), "PHARMACY", "");
			if(!ret.contains(temp)) ret.add(temp);
		}
		for (MedicineReservation mr : medicineReservationService.findAllDoneReservationsByPatient(patient.getId())) {
			temp = new NewComplaintDTO(mr.getPharmacy().getId(), mr.getPharmacy().getName(), "PHARMACY", "");
			if(!ret.contains(temp)) ret.add(temp);
		}
		for(ERecipe er : eRecipeRepository.findAllPharmaciesByPatient(patient)) {
			temp = new NewComplaintDTO(er.getPharmacy().getId(), er.getPharmacy().getName(), "PHARMACY", "");
			if(!ret.contains(temp)) ret.add(temp);
		}
		return ret;
	}
	
	

	@Override
	public Complaint createComplaint(Patient patient, NewComplaintDTO ncdto, Long complaintOn) {
		Complaint c = new Complaint();
		c.setPatient(patient);
		c.setComplaintEntityId(complaintOn);
		c.setComment(ncdto.getComplaint());
		c.setHandled(false);
		switch (ncdto.getComplaintEntityType()) {
		case "PHARMACY":
			c.setType(ComplaintType.PHARMACY);
			break;
		case "DERMATOLOGIST":
			c.setType(ComplaintType.DERMATOLOGIST);
			break;
		case "PHARMACIST":
			c.setType(ComplaintType.PHARMACIST);
			break;
		}
		boolean validated = false;
		for (NewComplaintDTO temp : findPossibleComplaints(patient))
			if(complaintOn.equals(temp.getComplaintEntityId()) && ncdto.getComplaintEntityType().equals(temp.getComplaintEntityType())) {
				validated = true;
				break;
			}
		if(!validated) return null;
		return complaintRepository.save(c);
	}

	@Override
	public List<NewRateDTO> findPossibleEntitiesForRate(Patient patient) {
		List<NewRateDTO> ret = new ArrayList<NewRateDTO>();
		ret.addAll(examinationService.getHealthWorkersForRate(patient.getId()));
		ret.addAll(medicineReservationService.getMedicinesForRate(patient.getId()));
		ret.addAll(pharmacyService.getPharmaciesForRate(patient.getId()));
		return ret;
	}

	@Override
	public void rate(NewRateDTO dto, Patient patient) {
		if(dto.getRateEntityType().equals("MEDICINE")) {
			RatedMedicine rm;
			rm = ratedEntitiesService.getRatedMedicineByPatientAndMedicine(patient.getId(), dto.getRateEntityId());
			if(rm != null)
				rm.setRate(dto.getRate());
			else {
				rm = new RatedMedicine();
				rm.setMedicine(medicineRepository.findById(dto.getRateEntityId()).orElse(null));
				rm.setRate(dto.getRate());
				rm.setPatient(patient);
			}
			ratedEntitiesService.saveRatedMedicine(rm);
		}
		else if(dto.getRateEntityType().equals("DERMATOLOGIST") || dto.getRateEntityType().equals("PHARMACIST")) {
			RatedHealthWorker r = ratedEntitiesService.getRatedHealthWorkerByPatientAndWorker(patient.getId(), dto.getRateEntityId());
			if(r != null)
				r.setRate(dto.getRate());
			else {
				r = new RatedHealthWorker();
				r.setHealthWorker((HealthWorker)userRepository.findById(dto.getRateEntityId()).orElse(null));
				r.setPatient(patient);
				r.setRate(dto.getRate());
			}
			ratedEntitiesService.saveRatedHealthWorker(r);
		}
		else if(dto.getRateEntityType().equals("PHARMACY")) {
			RatedPharmacy r = ratedEntitiesService.getRatedPharmacyByPatientAndPharmacy(patient.getId(), dto.getRateEntityId());
			if (r != null)
				r.setRate(dto.getRate());
			else {
				r = new RatedPharmacy();
				r.setPatient(patient);
				r.setRate(dto.getRate());
				r.setPharmacy(pharmacyService.getById(dto.getRateEntityId()));
			}
			ratedEntitiesService.saveRatedPharmacy(r);
		}
	}

}
