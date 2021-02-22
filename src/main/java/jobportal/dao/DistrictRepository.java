package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.District;
import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends CrudRepository <District, String>
{
    District findDistrictById(String id);
}