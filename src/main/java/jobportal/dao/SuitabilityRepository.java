package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Suitability;
import org.springframework.data.repository.CrudRepository;

public interface SuitabilityRepository extends CrudRepository <Suitability, Integer>
{
    Suitability findSuitabilityById(String id);
    Suitability findSuitabilityByCode(String code);
}