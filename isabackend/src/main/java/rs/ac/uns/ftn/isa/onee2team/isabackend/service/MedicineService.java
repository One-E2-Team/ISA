package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;

@Service
public class MedicineService implements IMedicineService {
	
	private IMedicineRepository medicineRepository;

	@Autowired
	public MedicineService(IMedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}
	
	@Override
	public List<MedicineDTO> findMedicineByPharmacyid(Long id){
		List<Medicine> allMedicines = medicineRepository.findMedicineByPharmacyid(id);
		List<MedicineDTO> medicinesDTO = new ArrayList<MedicineDTO>();
		for (Medicine m : allMedicines) {
			medicinesDTO.add(new MedicineDTO(m.getId(), m.getCode(), m.getName(), m.getContexture(), m.getManufacturer()));
		}
		return medicinesDTO;
	}

	@Override
	public List<Medicine> getAll() {
		return medicineRepository.findAll();
	}
	
	@Override
	public void addAllergy(Long patientId, Long medicineId) {
		medicineRepository.addAllergy(patientId, medicineId);
	}

}
