package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback.RatedHealthWorker;

public interface IRatedHealthWorkerRepository extends JpaRepository<RatedHealthWorker, Long> {
	
	@Query(value = "select rate from rated_health_workers where health_worker_id = ?1", nativeQuery = true)
	List<Integer> getRatesByHealthWorkerId(Long healthWorkerId);

}
