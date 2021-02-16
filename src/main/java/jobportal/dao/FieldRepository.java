package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Field;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository <Field, Integer>
{
    Field findFieldById(String id);
}