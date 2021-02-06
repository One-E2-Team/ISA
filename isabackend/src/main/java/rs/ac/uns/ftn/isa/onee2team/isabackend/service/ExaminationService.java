package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationAtDermatologistDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.User;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class ExaminationService implements IExaminationService {

	private IExaminationRepository examinationRepository;
	private IUserRepository userRepository;

	@Autowired
	public ExaminationService(IExaminationRepository examinationRepository, IUserRepository userRepository) {
		this.examinationRepository = examinationRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<ExaminationDTO> getFreeExaminationsByHealthWorkerIdAndPharmacyId(Long healthWorkerId, Long pharmacyId) {
		List<ExaminationDTO> ret = new ArrayList<ExaminationDTO>();
		for (Examination examination : examinationRepository
				.getFreeExaminationsByHealthWorkerIdAndPharmacyId(healthWorkerId, pharmacyId)) {
			ret.add(new ExaminationDTO(examination.getId(), healthWorkerId, pharmacyId, examination.getDate(),
					examination.getStartTime(), examination.getEndTime()));
		}
		return ret;
	}

	@Override
	public List<ExaminationAtDermatologistDTO> getFreeExaminationsAtDermatologist() {
		List<Examination> examinations = examinationRepository.getFreeExaminationsAtDermatologist();
		List<ExaminationAtDermatologistDTO> ret_list = new ArrayList<ExaminationAtDermatologistDTO>();
		for(Examination ex : examinations) {
			ret_list.add(new ExaminationAtDermatologistDTO(
						ex.getId(), ex.getStartTime().toString(), ex.getHealthWokrer().getId(), 
						ex.getHealthWokrer().getFirstName(), ex.getHealthWokrer().getLastName(), 
						ex.getPrice()
					));
		}
		
		return ret_list;
	}

}
