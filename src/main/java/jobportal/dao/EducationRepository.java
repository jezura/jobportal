package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Education;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepository extends CrudRepository <Education, Integer>
{
    Education findEducationById(String id);
    Education findEducationByCode(String code);
}