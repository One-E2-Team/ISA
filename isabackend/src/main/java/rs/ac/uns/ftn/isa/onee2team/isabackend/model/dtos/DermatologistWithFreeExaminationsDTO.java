package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.ArrayList;
import java.util.List;

public class DermatologistWithFreeExaminationsDTO {

	private CredentialsAndIdDTO credentials;
	private List<ExaminationDTO> freeExaminations = new ArrayList<ExaminationDTO>();

	public DermatologistWithFreeExaminationsDTO() {
	}

	public CredentialsAndIdDTO getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsAndIdDTO credentials) {
		this.credentials = credentials;
	}

	public List<ExaminationDTO> getFreeExaminations() {
		return freeExaminations;
	}

	public void setFreeExaminations(List<ExaminationDTO> freeExaminations) {
		this.freeExaminations = freeExaminations;
	}
}
