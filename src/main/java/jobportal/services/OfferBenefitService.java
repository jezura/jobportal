package jobportal.services;

import jobportal.dao.OfferBenefitRepository;
import jobportal.models.offer_data_models.codebooks.OfferBenefit;
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

    public OfferBenefit findOfferBenefitById(int id) {
        return offerBenefitRepository.findOfferBenefitById(id);
    }

    public void saveOfferBenefit(OfferBenefit ob){
        offerBenefitRepository.save(ob);
    }
}