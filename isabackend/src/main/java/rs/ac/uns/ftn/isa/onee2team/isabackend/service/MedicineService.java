package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.EquivalentMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IEquivalentMedicines;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;

@Service
public class MedicineService implements IMedicineService {
	
	private IMedicineRepository medicineRepository;
	private IEquivalentMedicines equivalentMedicines;

	@Autowired
	public MedicineService(IMedicineRepository medicineRepository, IEquivalentMedicines equivalentMedicines) {
		this.medicineRepository = medicineRepository;
		this.equivalentMedicines = equivalentMedicines;
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
		if(nmdto.getEquivalentMedicines()!=null)
			for(Medicine simm : nmdto.getEquivalentMedicines()) {
				EquivalentMedicine em = new EquivalentMedicine();
				em.setPrimaryMedicineId(m.getId());
				em.setPrimaryMedicineId(simm.getId());
				equivalentMedicines.save(em);
				EquivalentMedicine eminv = new EquivalentMedicine();
				eminv.setPrimaryMedicineId(simm.getId());
				eminv.setSimilarMedicineId(m.getId());
				equivalentMedicines.save(eminv);
			}
		return this.medicineRepository.save(m);
	}
}
