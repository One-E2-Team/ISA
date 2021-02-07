package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.ExaminationStatus;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IExaminationRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IPharmacyRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;

@Service
public class ExaminationService implements IExaminationService {

	private IExaminationRepository examinationRepository;
	private IUserRepository userRepository;
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	public ExaminationService(IExaminationRepository examinationRepository, IUserRepository userRepository, IPharmacyRepository pharmacyRepository) {
		this.examinationRepository = examinationRepository;
		this.userRepository = userRepository;
		this.pharmacyRepository = pharmacyRepository;
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
	public List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId,Date timeStart, Date timeEnd, ExaminationStatus status){

		List<ExaminationDTO> ret = new ArrayList<ExaminationDTO>();
		HealthWorker worker = (HealthWorker) userRepository.findById(healthWorkerId).orElse(null);
		for (Examination examination : examinationRepository.getExaminationsByHealthWorkerIdInTimeInterval(worker,timeStart,timeEnd,status)) {
			ret.add(new ExaminationDTO(examination.getId(),healthWorkerId,examination.getPharmacy().getId(),examination.getPharmacy().getName(),examination.getDate(),examination.getStartTime(),examination.getEndTime()));
		}
		return ret;
	}

	@Override
	public List<ExaminationDTO> getExaminationsByHealthWorkerIdInTimeInterval(Long healthWorkerId, Date timeStart,
			Date timeEnd, ExaminationStatus status, Long pharmacyId) {
		List<ExaminationDTO> ret = new ArrayList<ExaminationDTO>();
		HealthWorker worker = (HealthWorker) userRepository.findById(healthWorkerId).orElse(null);
		Pharmacy pharmacy = (Pharmacy) pharmacyRepository.findById(pharmacyId).orElse(null);
		for (Examination examination : examinationRepository.getExaminationsByHealthWorkerIdInTimeInterval(worker,timeStart,timeEnd,status,pharmacy)) {
			ret.add(new ExaminationDTO(examination.getId(),healthWorkerId,examination.getPharmacy().getId(),examination.getPharmacy().getName(),examination.getDate(),examination.getStartTime(),examination.getEndTime()));
		}
		return ret;
	}

}
