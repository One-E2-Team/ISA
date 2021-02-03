package rs.ac.uns.ftn.isa.onee2team.isabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.Promotion;

public interface IPromotionRepository extends JpaRepository<Promotion, Long> {

}
