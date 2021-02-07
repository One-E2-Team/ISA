package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
	
	@Query(value = "select * from examinations e where e.status = 0 and e.health_wokrer_id = ?1 and e.pharmacy_id = ?2", nativeQuery = true)
	List<Examination> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);
	
	@Query(value = "select e from Examination e where e.healthWokrer=?1 and e.startTime >= ?2 and e.endTime <= ?3 and e.status = ?4")
	List<Examination> getExaminationsByHealthWorkerIdInTimeInterval(HealthWorker healthWorker,Date from,Date to,ExaminationStatus status);
	
	@Query(value = "select e from Examination e where e.healthWokrer=?1 and e.startTime >= ?2 and e.endTime <= ?3 and e.status = ?4 and pharmacy = ?5")
			List<Examination> getExaminationsByHealthWorkerIdInTimeInterval(HealthWorker healthWorker,Date from,Date to,ExaminationStatus status, Pharmacy pharmacy);

}
