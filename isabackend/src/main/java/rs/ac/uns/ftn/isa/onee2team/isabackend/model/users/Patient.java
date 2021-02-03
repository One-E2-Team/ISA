package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {

	private static final long serialVersionUID = 1L;

	@Column(name = "points", nullable = true)
	private Integer points;

	@Column(name = "penalties", nullable = true)
	private Integer penalties;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "allergies", referencedColumnName = "id")
	private Set<Medicine> allergies;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}

	public Set<Medicine> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Medicine> allergies) {
		this.allergies = allergies;
	}
	
	@Override
	public String getUsername() {
		return this.getEmail();
	}
}
