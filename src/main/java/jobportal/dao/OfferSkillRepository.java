package jobportal.dao;

import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.codebooks.OfferSkill;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OfferSkillRepository extends CrudRepository <OfferSkill, Integer>
{
    OfferSkill findOfferSkillById(int id);
    List<OfferSkill> findByOffer(Offer offer);
}