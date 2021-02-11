package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class ScheduleNewExanimationDTO {
	private Long patientId;
	private Long examinationId;
	public ScheduleNewExanimationDTO(Long patientId, Long examinationId) {
		super();
		this.patientId = patientId;
		this.examinationId = examinationId;
	}
	public ScheduleNewExanimationDTO() {
		super();
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}
	
}
