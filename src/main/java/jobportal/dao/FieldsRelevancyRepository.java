package jobportal.dao;

import jobportal.models.internal_models.data_entites.FieldsRelevancy;
import org.springframework.data.repository.CrudRepository;

public interface FieldsRelevancyRepository extends CrudRepository <FieldsRelevancy, Integer>
{
    FieldsRelevancy findFieldsRelevancyById(int id);
}