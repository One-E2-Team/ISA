package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Loyalty;

public interface ILoyaltyRepository extends JpaRepository<Loyalty, Long> {
	ArrayList<Loyalty> findAllByType(CategoryType ct);
}
