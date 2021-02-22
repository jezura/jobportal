package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.OfferBenefit;
import org.springframework.data.repository.CrudRepository;

public interface OfferBenefitRepository extends CrudRepository <OfferBenefit, Integer>
{
    OfferBenefit findOfferBenefitById(int id);
}