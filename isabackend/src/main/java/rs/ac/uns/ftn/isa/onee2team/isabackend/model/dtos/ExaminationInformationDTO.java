package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class ExaminationInformationDTO {
	private Long examinationId;
	private String infromation;
	public ExaminationInformationDTO() {
		super();
	}
	public ExaminationInformationDTO(Long examinationId, String infromation) {
		super();
		this.examinationId = examinationId;
		this.infromation = infromation;
	}
	public Long getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}
	public String getInfromation() {
		return infromation;
	}
	public void setInfromation(String infromation) {
		this.infromation = infromation;
	}
}
