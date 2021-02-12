package rs.ac.uns.ftn.isa.onee2team.isabackend;


import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.NewRateDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.RequestReservationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ReservedMedicineDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.ScheduledExaminationDTO;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IExaminationService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IFeedbackService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IMedicineReservationService;
import rs.ac.uns.ftn.isa.onee2team.isabackend.service.IUserService;

@ContextConfiguration(classes = IsabackendApplication.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class Student1IntegrationTests {

	@Autowired
	IMedicineReservationService medicineReservationService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IFeedbackService feedbackService;
	
	@Autowired
	IExaminationService examinationService;
	
	@Test()
	@Sql(scripts = {"/cancelReservationdata.sql"})
	void cancelReservationTest() {
		
		medicineReservationService.cancelReservation(1L, 1L);
			
		List<ReservedMedicineDTO> lista = medicineReservationService.getPatientsReservations(1L);
		
		assertEquals(0, lista.size());
	}
	
	/*
	@SuppressWarnings("deprecation")
	@Test()
	void idk() {
		List<ScheduledExaminationDTO> lista = examinationService.getPatientsExaminations(1L);
		assertEquals(5.0, lista.get(0).getDoctorRate());
	}*/
	
	
	/*
	@Test
	@Sql(scripts = {"/reserveMedicinedata.sql"})
	void reserveMedicineTest() {
		RequestReservationDTO dto = new RequestReservationDTO();
		dto.setExpireDate(new Date(System.currentTimeMillis()));
		dto.setMedicine_id(1L);
		dto.setPharmacy_id(1L);
		
		medicineReservationService.reserve(dto, 1L);
		
		assertEquals(1, medicineReservationService.getPatientsReservations(1L).size());
	}*/
	
	/*
	@Test
	@Sql(scripts = {"/ratedata.sql"})
	void rateMedicineTest() {
		NewRateDTO dto = new NewRateDTO();
		dto.setRate(3);
		dto.setRateEntityId(1L);
		dto.setRateEntityName("lek 1");
		dto.setRateEntityType("MEDICINE");
		
		Patient p = (Patient) userService.findById(1L);
		
		feedbackService.rate(dto, p);
		
		List<NewRateDTO> list = medicineReservationService.getMedicinesForRate(1L);
		
		assertEquals(3, (int)list.get(0).getRate());
	}*/
}

