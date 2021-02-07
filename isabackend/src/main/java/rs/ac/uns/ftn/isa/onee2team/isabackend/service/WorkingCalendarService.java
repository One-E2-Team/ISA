package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWorkingCalendarRepository;

@Service
public class WorkingCalendarService implements IWorkingCalendarService {

	private IWorkingCalendarRepository workingCalendarRepository;
	private IUserRepository userRepository;

	@Autowired
	public WorkingCalendarService(IWorkingCalendarRepository workingCalendarRepository,
			IUserRepository userRepository) {
		this.workingCalendarRepository = workingCalendarRepository;
		this.userRepository = userRepository;
	}

	@Override
	public WorkingCalendar getWorkingTimeForDermatologist(Long dermatologistId, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if (admin == null)
			return null;
		WorkingCalendar ret = new WorkingCalendar();
		Integer count = workingCalendarRepository.getNumOfVacationsForHealthWorkerIdAndDate(dermatologistId,
				new Date());
		if (count > 0) {
			ret.setId(-1L);
			return ret;
		}
		ret = workingCalendarRepository.getWorkingTimeForDermatologist(dermatologistId, admin.getPharmacy().getId());
		return ret;
	}

}
