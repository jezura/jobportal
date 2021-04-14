package jobportal.dao;

import jobportal.models.offer_data_models.FirstContact;
import org.springframework.data.repository.CrudRepository;

public interface FirstContactRepository extends CrudRepository <FirstContact, Integer>
{
    FirstContact findFirstContactById(int id);
}