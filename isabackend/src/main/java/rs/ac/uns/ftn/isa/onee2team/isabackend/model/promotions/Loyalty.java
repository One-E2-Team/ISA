package rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loyalty")
public class Loyalty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "type", nullable = false)
	private CategoryType type;

	@Column(name = "minPoints", nullable = false)
	private Integer minPoints;

	@Column(name = "discount", nullable = false)
	private Double discount;

	@Column(name = "examinationPoints", nullable = false)
	private Integer examinationPoints;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public Integer getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getExaminationPoints() {
		return examinationPoints;
	}

	public void setExaminationPoints(Integer examinationPoints) {
		this.examinationPoints = examinationPoints;
	}
}
