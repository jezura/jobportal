package jobportal.dao;

import jobportal.models.offer_data_models.codebooks.Workship;
import org.springframework.data.repository.CrudRepository;

public interface WorkshipRepository extends CrudRepository <Workship, String>
{
    Workship findWorkshipById(String id);
    Workship findWorkshipByCode(String code);
}