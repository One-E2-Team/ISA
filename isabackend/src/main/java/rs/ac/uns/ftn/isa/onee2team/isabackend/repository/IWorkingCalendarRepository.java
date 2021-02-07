package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;

public interface IWorkingCalendarRepository extends JpaRepository<WorkingCalendar, Long> {
	
	@Query(value = "select * from working_calendars c where c.pharmacy_id = ?2 and c.health_wokrer_id = ?1 and c.health_wokrer_id in "
			+ "(select u.id from all_users u where u.user_type = 2) and c.start_date <= ?3 and c.end_date >= ?3", nativeQuery = true)
	WorkingCalendar getWorkingTimeForDermatologist(Long dermatologistId, Long pharmacyId, Date date);
	
	@Query(value = "select count(*) from vacation_requests v where v.health_wokrer_id = ?1 and v.accepted = true "
			+ "and v.start_date < ?2 and v.end_date > ?2", nativeQuery = true)
	Integer getNumOfVacationsForHealthWorkerIdAndDate(Long healthWorkerId, Date date);
}
