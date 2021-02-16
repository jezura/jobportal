package jobportal.dao;


import jobportal.models.internal_models.cv_support.CzechName;
import org.springframework.data.repository.CrudRepository;

public interface CzechNameRepository extends CrudRepository <CzechName, Integer>
{
    CzechName findCzechNameById(int id);
    CzechName findCzechNameByName(String firstName);
}