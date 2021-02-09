package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class OfferDTO {

	private Long id;
	private Double fullPrice;
	private Date date;
	private String dealerName;
	
	public OfferDTO() {}

	public OfferDTO(Long id, Double fullPrice, Date date, String dealerName) {
		super();
		this.id = id;
		this.fullPrice = fullPrice;
		this.date = date;
		this.dealerName = dealerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(Double fullPrice) {
		this.fullPrice = fullPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
}
