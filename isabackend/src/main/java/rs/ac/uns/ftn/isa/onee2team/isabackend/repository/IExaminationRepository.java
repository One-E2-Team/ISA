package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
	
	@Query(value = "select * from examinations e where e.status = 0 and e.health_wokrer_id = ?1 and e.pharmacy_id = ?2", nativeQuery = true)
	List<Examination> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId);
}