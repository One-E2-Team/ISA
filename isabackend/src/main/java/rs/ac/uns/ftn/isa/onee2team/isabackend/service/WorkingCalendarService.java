package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.WorkingTimeDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWorkingCalendarRepository;

@Service
public class WorkingCalendarService implements IWorkingCalendarService {

	private IWorkingCalendarRepository workingCalendarRepository;
	private IUserRepository userRepository;
	private IExaminationRepository examinationRepository;

	@Autowired
	public WorkingCalendarService(IWorkingCalendarRepository workingCalendarRepository, IUserRepository userRepository,
			IExaminationRepository examinationRepository) {
		this.workingCalendarRepository = workingCalendarRepository;
		this.userRepository = userRepository;
		this.examinationRepository = examinationRepository;
	}

	@Override
	public WorkingCalendar getWorkingTimeForDermatologist(WorkingTimeDTO dto, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		System.out.println("*********************************************************");
		if (admin == null)
			return null;
		WorkingCalendar ret = new WorkingCalendar();
		List<Long> dermatologistsPharmacies = userRepository.getPharmacyIdsForDermatologist(dto.getDermatologistId());
		if(!dermatologistsPharmacies.contains(admin.getPharmacy().getId())) {
			ret.setId(-3l);
			return ret;
		}
		Integer countOfVacations = workingCalendarRepository.getNumOfVacationsForHealthWorkerIdAndDate(dto.getDermatologistId(),
				dto.getDate());
		if (countOfVacations > 0) {
			ret.setId(-1L);
			return ret;
		}
		Integer countOfExams = examinationRepository.getNumFreeExaminationsForHealthWorkerInPharmacyInDate(
				dto.getDermatologistId(), dto.getDate(), admin.getPharmacy().getId());
		if(countOfExams > 1) {
			ret.setId(-2L);
			return ret;
		}
		ret = workingCalendarRepository.getWorkingTimeForDermatologist(dto.getDermatologistId(),
				admin.getPharmacy().getId(), dto.getDate());
		return ret;
	}

}
