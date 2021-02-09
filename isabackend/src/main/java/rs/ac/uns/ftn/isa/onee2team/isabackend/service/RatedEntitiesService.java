package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedHealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedPharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedHealthWorkerRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRatedPharmacyRepository;

@Service
public class RatedEntitiesService implements IRatedEntitiesService{

	private IRatedMedicineRepository ratedMedicineRepository;
	private IRatedHealthWorkerRepository ratedHealthWorkerRepository;
	private IRatedPharmacyRepository ratedPharmacyRepository;
	
	@Autowired
	public RatedEntitiesService(IRatedMedicineRepository ratedMedicineRepository,
			IRatedHealthWorkerRepository ratedHealthWorkerRepository, 
			IRatedPharmacyRepository ratedPharmacyRepository) {
		this.ratedMedicineRepository = ratedMedicineRepository;
		this.ratedHealthWorkerRepository = ratedHealthWorkerRepository;
		this.ratedPharmacyRepository = ratedPharmacyRepository;
	}
	
	@Override
	public RatedMedicine getRatedMedicineByPatientAndMedicine(Long patient_id, Long medicine_id) {
		return ratedMedicineRepository.getRatedMedicineByPatientAndMedicine(patient_id, medicine_id);
	}

	@Override
	public RatedHealthWorker getRatedHealthWorkerByPatientAndWorker(Long patient_id, Long worker_id) {
		return ratedHealthWorkerRepository.getRatedHealthWorkerByPatientAndWorker(patient_id, worker_id);
	}

	@Override
	public RatedPharmacy getRatedPharmacyByPatientAndPharmacy(Long patient_id, Long pharmacy_id) {
		return ratedPharmacyRepository.getRatedPharmacyByPatientAndPharmacy(patient_id, pharmacy_id);
	}

	@Override
	public void saveRatedMedicine(RatedMedicine r) {
		ratedMedicineRepository.save(r);
		
	}

	@Override
	public void saveRatedHealthWorker(RatedHealthWorker r) {
		ratedHealthWorkerRepository.save(r);
		
	}

	@Override
	public void saveRatedPharmacy(RatedPharmacy r) {
		ratedPharmacyRepository.save(r);
	}

	
}
