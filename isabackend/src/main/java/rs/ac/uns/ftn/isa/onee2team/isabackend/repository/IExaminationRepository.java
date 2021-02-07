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

	@Query(value = "select * from examinations e where e.status in (0,2) and e.health_wokrer_id = ?1 and e.pharmacy_id = ?2", nativeQuery = true)
	List<Examination> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);

	
	@Query(value = "select e from Examination e where e.healthWokrer=?1 and e.startTime >= ?2 and e.endTime <= ?3 and e.status = ?4")
	List<Examination> getExaminationsByHealthWorkerIdInTimeInterval(HealthWorker healthWorker,Date from,Date to,ExaminationStatus status);
	
	@Query(value = "select e from Examination e where e.healthWokrer=?1 and e.startTime >= ?2 and e.endTime <= ?3 and e.status = ?4 and pharmacy = ?5")
			List<Examination> getExaminationsByHealthWorkerIdInTimeInterval(HealthWorker healthWorker,Date from,Date to,ExaminationStatus status, Pharmacy pharmacy);


	@Query(value = "select * from examinations e where e.status in (0,2) and e.health_wokrer_id in \r\n"
			+ "(select user_id from user_authority where authority_id = 3)", nativeQuery = true)
	List<Examination> getFreeExaminationsAtDermatologist();

	@Query(value = "select * from examinations e where e.status in (0,2) and e.health_wokrer_id = ?1 and e.pharmacy_id in "
			+ "(select u.pharmacists_pharmacy_id from all_users u where u.user_type = 1 and u.id = ?1)", nativeQuery = true)
	List<Examination> getFreeExaminationsByPharmacistId(Long pharmacistId);

	@Query(value = "select count(*) from examinations e where e.status = 4 and e.health_wokrer_id = ?1 "
			+ "and e.date > ?2 and e.date < ?3 and e.pharmacy_id in "
			+ "(select u.pharmacists_pharmacy_id from all_users u where u.user_type = 1 and u.id = ?1)", nativeQuery = true)
	Integer getNumScheduledExaminationsByPharmacistIdInTimeInterval(Long pharmacistId, Date start, Date end);

	@Query(value = "select count(*) from examinations e where e.status = 4 and e.health_wokrer_id = ?1 "
			+ "and e.date > ?2 and e.date < ?3 and e.pharmacy_id in "
			+ "(select d.pharmacies_id from dermatologists_in_pharmacies d where d.dermatologist_id = ?1)", nativeQuery = true)
	Integer getNumScheduledExaminationsByDermatologistsIdInTimeInterval(Long dermatologistsId, Date start, Date end);

	@Query(value = "select e from Examination e where e.patient.id = ?1 and e.status = 4 ")
	List<Examination> getScheduledAppointments(Long patientId);

}
