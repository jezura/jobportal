package jobportal.services;

import jobportal.dao.OfferSkillRepository;
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

    public OfferSkill findOfferSkillById(String id) {
        return offerSkillRepository.findOfferSkillById(id);
    }

    public void saveOfferSkill(OfferSkill os){
        offerSkillRepository.save(os);
    }
}