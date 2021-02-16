package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Benefit;
import org.springframework.data.repository.CrudRepository;

public interface BenefitRepository extends CrudRepository <Benefit, Integer>
{
    Benefit findBenefitById(String id);
}