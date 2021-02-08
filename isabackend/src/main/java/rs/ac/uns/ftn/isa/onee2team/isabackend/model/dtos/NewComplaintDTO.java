package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class NewComplaintDTO {
	private Long complaintEntityId;
	private String complainedEntityName;
	private String complaintEntityType;
	private String complaint;
	
	public Long getComplaintEntityId() {
		return complaintEntityId;
	}
	public void setComplaintEntityId(Long complaintEntityId) {
		this.complaintEntityId = complaintEntityId;
	}
	public String getComplaintEntityType() {
		return complaintEntityType;
	}
	public void setComplaintEntityType(String complaintEntityType) {
		this.complaintEntityType = complaintEntityType;
	}
	public String getComplaint() {
		return complaint;
	}
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	public NewComplaintDTO(Long complaintEntityId, String complainedEntityName, String complaintEntityType, String complaint) {
		super();
		this.complaintEntityId = complaintEntityId;
		this.complainedEntityName = complainedEntityName;
		this.complaintEntityType = complaintEntityType;
		this.complaint = complaint;
	}
	public NewComplaintDTO() {
		super();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NewComplaintDTO)) { 
            return false; 
        }
		return ((NewComplaintDTO) obj).complaintEntityId.equals(this.complaintEntityId) &&
				((NewComplaintDTO) obj).complaintEntityType.equals(this.complaintEntityType);
	}
	public String getComplainedEntityName() {
		return complainedEntityName;
	}
	public void setComplainedEntityName(String complainedEntityName) {
		this.complainedEntityName = complainedEntityName;
	}
}
