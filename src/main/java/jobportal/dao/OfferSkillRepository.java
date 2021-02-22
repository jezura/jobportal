package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.OfferSkill;
import org.springframework.data.repository.CrudRepository;

public interface OfferSkillRepository extends CrudRepository <OfferSkill, Integer>
{
    OfferSkill findOfferSkillById(int id);
}