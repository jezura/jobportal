package jobportal.services;

import jobportal.dao.OfferBenefitRepository;
import jobportal.dao.OfferSkillRepository;
import jobportal.models.OfferBenefit;
import jobportal.models.OfferSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OfferBenefitService {
    @Autowired
    private OfferBenefitRepository offerBenefitRepository;

    public Collection<OfferBenefit> findAllOfferBenefits(){
        List<OfferBenefit> offerBenefits = new ArrayList<OfferBenefit>();
        for (OfferBenefit offerBenefit:offerBenefitRepository.findAll())
        {
            offerBenefits.add(offerBenefit);
        }
        return offerBenefits;
    }

    public OfferBenefit findOfferBenefitById(String id) {
        return offerBenefitRepository.findOfferBenefitById(id);
    }

    public void saveOfferBenefit(OfferBenefit ob){
        offerBenefitRepository.save(ob);
    }
}