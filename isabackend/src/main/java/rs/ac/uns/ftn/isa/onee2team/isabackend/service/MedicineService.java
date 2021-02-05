package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineDTO;
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
	public Medicine createMedicine(NewMedicineDTO nmdto) {
		Medicine m = new Medicine();
		m.setCode(nmdto.getCode());
		m.setContexture(nmdto.getContexture());
		m.setDailyIntake(nmdto.getDailyIntake());
		m.setManufacturer(nmdto.getManufacturer());
		m.setMedicineForm(nmdto.getMedicineForm());
		m.setMedicineType(nmdto.getMedicineType());
		m.setName(nmdto.getName());
		m.setPoints(nmdto.getPoints());
		m.setRecipeNeeded(nmdto.getRecipeNeeded());
		m.setSideEffects(nmdto.getSideEffects());
		m.setEquivalentMedicines(nmdto.getEquivalentMedicines());
		return this.medicineRepository.save(m);
	}
}
