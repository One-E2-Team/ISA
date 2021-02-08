package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class ExaminationInformationDTO {
	private Long examinationId;
	private String information;
	public ExaminationInformationDTO() {
		super();
	}
	public ExaminationInformationDTO(Long examinationId, String information) {
		super();
		this.examinationId = examinationId;
		this.information = information;
	}
	public Long getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}
	public String getInfromation() {
		return information;
	}
	public void setInfromation(String infromation) {
		this.information = infromation;
	}
}
