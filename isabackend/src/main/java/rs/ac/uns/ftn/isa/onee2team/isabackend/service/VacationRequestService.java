package rs.ac.uns.ftn.isa.onee2team.isabackend.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.VacationRequest;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IVacationRequestRepository;
@Service
public class VacationRequestService implements IVacationRequestService{

private IVacationRequestRepository vacationRequestRepository;
private IUserRepository userRepository;
	
	@Autowired
	public VacationRequestService(IVacationRequestRepository vacationRequestRepository, IUserRepository userRepository) {
		this.vacationRequestRepository = vacationRequestRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void save(VacationRequestDTO vacReq) {
		VacationRequest vr = new VacationRequest();
		vr.setStartDate(vacReq.getStart());
		vr.setEndDate(vacReq.getEnd());
		vr.setType(vacReq.getType());
		vr.setHealthWorker((HealthWorker)userRepository.findById(vacReq.getHealthWorkerId()).orElse(null));
		vacationRequestRepository.save(vr);
	}
}
