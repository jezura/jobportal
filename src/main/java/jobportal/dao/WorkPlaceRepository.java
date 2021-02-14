package jobportal.dao;


import jobportal.models.WorkPlace;
import jobportal.models.Workshift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkPlaceRepository extends CrudRepository <WorkPlace, Integer>
{
    WorkPlace findWorkPlaceById(int id);
}