package jobportal.dao;

import jobportal.models.offer_data_models.codebooks.Benefit;
import org.springframework.data.repository.CrudRepository;

public interface BenefitRepository extends CrudRepository <Benefit, String>
{
    Benefit findBenefitById(String id);
}