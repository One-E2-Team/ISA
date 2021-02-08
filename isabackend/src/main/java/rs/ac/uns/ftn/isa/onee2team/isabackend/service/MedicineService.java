package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.MedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestForMissingMedicinesDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.RequestForMissingMedicines;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.EquivalentMedicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Warehouse;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.PharmacyAdmin;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IEquivalentMedicines;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IMedicineRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IRequestForMissingMedicinesRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IUserRepository;
import rs.ac.uns.ftn.isa.onee2team.isabackend.repository.IWarehouseRepository;

@Service
public class MedicineService implements IMedicineService {

	private IMedicineRepository medicineRepository;
	private IEquivalentMedicines equivalentMedicines;
	private IUserRepository userRepository;
	private IRequestForMissingMedicinesRepository requestForMissingMedicinesRepository;
	private IWarehouseRepository warehouseRepository;

	@Autowired
	public MedicineService(IMedicineRepository medicineRepository, IEquivalentMedicines equivalentMedicines,
			IUserRepository userRepository,
			IRequestForMissingMedicinesRepository requestForMissingMedicinesRepository, IWarehouseRepository warehouseRepository) {
		this.medicineRepository = medicineRepository;
		this.equivalentMedicines = equivalentMedicines;
		this.userRepository = userRepository;
		this.requestForMissingMedicinesRepository = requestForMissingMedicinesRepository;
		this.warehouseRepository = warehouseRepository;
	}

	@Override
	public List<MedicineDTO> findMedicineByPharmacyid(Long id) {
		List<Medicine> allMedicines = medicineRepository.findMedicineByPharmacyid(id);
		List<MedicineDTO> medicinesDTO = new ArrayList<MedicineDTO>();
		for (Medicine m : allMedicines) {
			medicinesDTO
					.add(new MedicineDTO(m.getId(), m.getCode(), m.getName(), m.getContexture(), m.getManufacturer()));
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
		m = this.medicineRepository.save(m);
		if (nmdto.getEquivalentMedicines() != null)
			for (Medicine simm : nmdto.getEquivalentMedicines()) {
				EquivalentMedicine em = new EquivalentMedicine();
				em.setPrimaryMedicineId(m.getId());
				em.setSimilarMedicineId(simm.getId());
				equivalentMedicines.save(em);
				EquivalentMedicine eminv = new EquivalentMedicine();
				eminv.setPrimaryMedicineId(simm.getId());
				eminv.setSimilarMedicineId(m.getId());
				equivalentMedicines.save(eminv);
			}
		return m;
	}

	@Override
	public List<RequestForMissingMedicinesDTO> getMissingMedicines(Long userId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(userId).orElse(null);
		if(admin == null)
			return null;
		List<RequestForMissingMedicinesDTO> ret = new ArrayList<RequestForMissingMedicinesDTO>();
		for (RequestForMissingMedicines req : requestForMissingMedicinesRepository.getAllByPharmacy(admin.getPharmacy().getId())) {
			RequestForMissingMedicinesDTO dto = new RequestForMissingMedicinesDTO(req.getId(), req.getDate(), req.getMedicine().getId());
			ret.add(dto);
		}
		return ret;
	}

	@Override
	public Boolean deleteMedicineFromPharmacy(Long medicineId, Long loggedUserId) {
		PharmacyAdmin admin = (PharmacyAdmin) userRepository.findById(loggedUserId).orElse(null);
		if(admin == null)
			return null;
		List<Long> medicinesInPharmacy = warehouseRepository.getAllMedicinesInPharmacy(admin.getPharmacy().getId());
		if(!medicinesInPharmacy.contains(medicineId))
			return false;
		Warehouse warehouse = warehouseRepository.getByMedicineAndPharmacy(medicineId, admin.getPharmacy().getId());
		if(warehouse.getReservedAmount() == 0) {
			warehouse.setAmount(0);
			warehouseRepository.save(warehouse);
			return true;
		}
		return false;
	}
}
