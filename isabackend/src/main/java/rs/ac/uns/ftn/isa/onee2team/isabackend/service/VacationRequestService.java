package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.VacationRequest;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.VacationRequestWithHealthWorkerDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IVacationRequestRepository;

@Service
public class VacationRequestService implements IVacationRequestService {

	private IVacationRequestRepository vacationRequestRepository;
	private IUserRepository userRepository;

	@Autowired
	public VacationRequestService(IVacationRequestRepository vacationRequestRepository,
			IUserRepository userRepository) {
		this.vacationRequestRepository = vacationRequestRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void save(VacationRequestDTO vacReq) {
		VacationRequest vr = new VacationRequest();
		vr.setStartDate(vacReq.getStart());
		vr.setEndDate(vacReq.getEnd());
		vr.setType(vacReq.getType());
		vr.setHealthWorker((HealthWorker) userRepository.findById(vacReq.getHealthWorkerId()).orElse(null));
		vacationRequestRepository.save(vr);
	}

	@Override
	public List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromDermatologists() {
		return getProperDto(false, null);
	}

	@Override
	public List<VacationRequestWithHealthWorkerDTO> getAllVacationRequestsFromPharmacists(Long adminId) {
		return getProperDto(true, adminId);
	}

	private List<VacationRequestWithHealthWorkerDTO> getProperDto(Boolean isPharmacist, Long adminId) {
		List<VacationRequestWithHealthWorkerDTO> ret = new ArrayList<VacationRequestWithHealthWorkerDTO>();
		List<VacationRequest> requests = new ArrayList<VacationRequest>();
		if (isPharmacist) {
			PharmacyAdmin loggedAdmin = (PharmacyAdmin) userRepository.findById(adminId).orElse(null);
			requests = vacationRequestRepository
					.getAllVacationRequestsFromPharmacistsByPharmacyId(loggedAdmin.getPharmacy().getId());
		} else
			requests = vacationRequestRepository.getAllVacationRequestsFromDermatologists();

		for (VacationRequest request : requests) {
			HealthWorker worker = request.getHealthWorker();
			VacationRequestWithHealthWorkerDTO dto = new VacationRequestWithHealthWorkerDTO(request.getId(), request.getStartDate(), request.getEndDate(), request.getType(), worker.getId(),
					worker.getFirstName(), worker.getLastName(), request.getAccepted());
			ret.add(dto);
		}
		return ret;
	}
}
