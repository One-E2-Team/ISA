package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedHealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedPharmacy;

public interface IRatedEntitiesService {

	RatedMedicine getRatedMedicineByPatientAndMedicine(Long patient_id, Long medicine_id);
	RatedHealthWorker getRatedHealthWorkerByPatientAndWorker(Long patient_id, Long worker_id);
	RatedPharmacy getRatedPharmacyByPatientAndPharmacy(Long patient_id, Long pharmacy_id);
	
	void saveRatedMedicine(RatedMedicine r);
	void saveRatedHealthWorker(RatedHealthWorker r);
	void saveRatedPharmacy(RatedPharmacy r);
}
