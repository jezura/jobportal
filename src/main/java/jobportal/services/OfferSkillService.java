package jobportal.services;

import jobportal.dao.OfferSkillRepository;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.codebooks.OfferSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OfferSkillService {
    @Autowired
    private OfferSkillRepository offerSkillRepository;

    public Collection<OfferSkill> findAllOfferSkills(){
        List<OfferSkill> offerSkills = new ArrayList<OfferSkill>();
        for (OfferSkill offerSkill:offerSkillRepository.findAll())
        {
            offerSkills.add(offerSkill);
        }
        return offerSkills;
    }

    public Collection<OfferSkill> findAllByOffer(Offer offer){
        List<OfferSkill> offerSkills = new ArrayList<OfferSkill>();
        for (OfferSkill offerSkill:offerSkillRepository.findByOffer(offer))
        {
            offerSkills.add(offerSkill);
        }
        return offerSkills;
    }

    public OfferSkill findOfferSkillById(int id) {
        return offerSkillRepository.findOfferSkillById(id);
    }

    public void saveOfferSkill(OfferSkill os){
        offerSkillRepository.save(os);
    }
}