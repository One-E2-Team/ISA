package rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dealer;

@Entity
@Table(name = "offers")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)//, fetch = FetchType.LAZY
	@JoinColumn(name = "orderId")
	private Order order;

	@Column(name = "fullPrice", nullable = false)
	private Double fullPrice;

	@Column(name = "date", nullable = false)
	private Date date;

	@ManyToOne(optional = false)
	@JoinColumn(name = "dealerId")
	private Dealer dealer;

	@Column(name = "status", nullable = false)
	private OfferStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}
}
