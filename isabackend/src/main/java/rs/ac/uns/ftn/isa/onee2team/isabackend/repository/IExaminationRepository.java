package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
	
	@Query(value = "select * from examinations e where e.status = 0 and e.health_wokrer_id = ?1 and e.pharmacy_id = ?2", nativeQuery = true)
	List<Examination> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);
	
	@Query(value = "select * from examinations e where e.status = 0 and e.health_wokrer_id = ?1 and e.pharmacy_id in "
			+ "(select u.pharmacists_pharmacy_id from all_users u where u.user_type = 1 and u.id = ?1)", nativeQuery = true)
	List<Examination> getFreeExaminationsByPharmacistId(Long pharmacistId);
	
	@Query(value = "select count(*) from examinations e where e.status != 0 and e.health_wokrer_id = ?1 \r\n"
			+ "and e.date > ?2 and e.date < ?3 and e.pharmacy_id in \r\n"
			+ "(select u.pharmacists_pharmacy_id from all_users u where u.user_type = 1 and u.id = ?1)", nativeQuery = true)
	Integer getNumScheduledExaminationsByPharmacistIdInTimeInterval(Long pharmacistId, Date start, Date end);
}
