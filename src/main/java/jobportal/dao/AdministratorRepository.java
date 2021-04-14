package jobportal.dao;

import jobportal.models.internal_models.data_entites.user.Administrator;
import org.springframework.data.repository.CrudRepository;

public interface AdministratorRepository extends CrudRepository <Administrator, Integer>
{
    Administrator findAdministratorByEmail(String email);
}