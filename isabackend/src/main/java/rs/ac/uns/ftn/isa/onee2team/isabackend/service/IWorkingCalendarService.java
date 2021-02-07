package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos.WorkingTimeDTO;

public interface IWorkingCalendarService {
	
	WorkingCalendar getWorkingTimeForDermatologist(WorkingTimeDTO dto, Long loggedUserId);
}
