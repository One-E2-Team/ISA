package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class PricelistDTO {

	private Long pricelistId;
	private Long medicineId;
	private Long pharmacyId;
	private Double price;
	private Date startDate;
	private Date endDate;

	public PricelistDTO() {
	}

	public PricelistDTO(Long pricelistId, Long medicineId, Double price, Date startDate, Date endDate, Long pharmacyId) {
		this.pricelistId = pricelistId;
		this.medicineId = medicineId;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pharmacyId = pharmacyId;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Long getPricelistId() {
		return pricelistId;
	}

	public void setPricelistId(Long pricelistId) {
		this.pricelistId = pricelistId;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
