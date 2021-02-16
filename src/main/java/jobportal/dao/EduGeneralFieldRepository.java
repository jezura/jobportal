package jobportal.dao;


import jobportal.models.internal_models.codebooks.EduGeneralField;
import org.springframework.data.repository.CrudRepository;

public interface EduGeneralFieldRepository extends CrudRepository <EduGeneralField, Integer>
{
    EduGeneralField findEduGeneralFieldByAnnCode(int annCode);
    EduGeneralField findEduGeneralFieldByName(String name);
}