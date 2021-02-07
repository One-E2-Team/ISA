package rs.ac.uns.ftn.isa.onee2team.isabackend.service;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.WorkingCalendar;

public interface IWorkingCalendarService {
	
	WorkingCalendar getWorkingTimeForDermatologist(Long dermatologistId, Long loggedUserId);
}
