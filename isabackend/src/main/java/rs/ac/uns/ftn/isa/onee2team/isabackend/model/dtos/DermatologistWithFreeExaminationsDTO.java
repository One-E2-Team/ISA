package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;

public class DermatologistWithFreeExaminationsDTO {

	private CredentialsAndIdDTO credentials;
	private List<Examination> freeExaminations = new ArrayList<Examination>();

	public DermatologistWithFreeExaminationsDTO() {
	}

	public CredentialsAndIdDTO getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsAndIdDTO credentials) {
		this.credentials = credentials;
	}

	public List<Examination> getFreeExaminations() {
		return freeExaminations;
	}

	public void setFreeExaminations(List<Examination> freeExaminations) {
		this.freeExaminations = freeExaminations;
	}
}
