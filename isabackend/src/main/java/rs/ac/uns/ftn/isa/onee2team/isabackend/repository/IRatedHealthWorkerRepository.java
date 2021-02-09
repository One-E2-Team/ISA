package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedHealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedMedicine;

public interface IRatedHealthWorkerRepository extends JpaRepository<RatedHealthWorker, Long> {

	@Query(value = "select IFNULL(avg(rate), 0) from rated_health_workers where health_worker_id = ?1", nativeQuery = true)
	Double getAverageRateByHealthWorkerId(Long healthWorkerId);

	@Query("select r from RatedHealthWorker r where r.patient.id = ?1 and r.healthWorker.id = ?2")
	public RatedHealthWorker getRatedHealthWorkerByPatientAndWorker(Long patient_id, Long worker_id);
}
