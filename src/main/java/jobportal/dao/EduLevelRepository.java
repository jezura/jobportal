package jobportal.dao;


import jobportal.models.internal_models.codebooks.EduLevel;
import org.springframework.data.repository.CrudRepository;

public interface EduLevelRepository extends CrudRepository <EduLevel, Integer>
{
    EduLevel findEduLevelById(int id);
    EduLevel findEduLevelByAnnCode(int annCode);
    EduLevel findEduLevelByName(String name);
}