package jobportal.dao;


import jobportal.models.offer_data_models.WorkPlace;
import org.springframework.data.repository.CrudRepository;

public interface WorkPlaceRepository extends CrudRepository <WorkPlace, Integer>
{
    WorkPlace findWorkPlaceById(int id);
}