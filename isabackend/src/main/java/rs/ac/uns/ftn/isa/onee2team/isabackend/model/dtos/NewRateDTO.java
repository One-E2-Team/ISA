package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class NewRateDTO {

	private Long rateEntityId;
	private String rateEntityName;
	private String rateEntityType;
	private Integer rate;

	public NewRateDTO() {}
	
	public NewRateDTO(Long rateEntityId, String rateEntityName, String rateEntityType, Integer rate) {
		super();
		this.rateEntityId = rateEntityId;
		this.rateEntityName = rateEntityName;
		this.rateEntityType = rateEntityType;
		this.rate = rate;
	}
	
	public Long getRateEntityId() {
		return rateEntityId;
	}
	public void setRateEntityId(Long rateEntityId) {
		this.rateEntityId = rateEntityId;
	}
	public String getRateEntityName() {
		return rateEntityName;
	}
	public void setRateEntityName(String rateEntityName) {
		this.rateEntityName = rateEntityName;
	}
	public String getRateEntityType() {
		return rateEntityType;
	}
	public void setRateEntityType(String rateEntityType) {
		this.rateEntityType = rateEntityType;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((rateEntityId == null) ? 0 : rateEntityId.hashCode());
		result = prime * result + ((rateEntityName == null) ? 0 : rateEntityName.hashCode());
		result = prime * result + ((rateEntityType == null) ? 0 : rateEntityType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewRateDTO other = (NewRateDTO) obj;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (rateEntityId == null) {
			if (other.rateEntityId != null)
				return false;
		} else if (!rateEntityId.equals(other.rateEntityId))
			return false;
		if (rateEntityName == null) {
			if (other.rateEntityName != null)
				return false;
		} else if (!rateEntityName.equals(other.rateEntityName))
			return false;
		if (rateEntityType == null) {
			if (other.rateEntityType != null)
				return false;
		} else if (!rateEntityType.equals(other.rateEntityType))
			return false;
		return true;
	}
}
