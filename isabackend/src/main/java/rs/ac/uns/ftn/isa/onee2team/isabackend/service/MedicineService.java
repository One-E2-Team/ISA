package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

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
	public List<Medicine> findMedicineByPharmacyid(Long id){
		return this.medicineRepository.findMedicineByPharmacyid(id);
	}
}
