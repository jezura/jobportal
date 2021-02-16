package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Skill;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository <Skill, Integer>
{
    Skill findSkillById(String id);
}