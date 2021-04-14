package jobportal.dao;

import jobportal.models.offer_data_models.codebooks.Workshift;
import org.springframework.data.repository.CrudRepository;

public interface WorkshiftRepository extends CrudRepository <Workshift, String>
{
    Workshift findWorkshiftById(String id);
    Workshift findWorkshiftByCode(String code);
}