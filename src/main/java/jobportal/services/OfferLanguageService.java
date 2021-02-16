package jobportal.services;

import jobportal.dao.OfferLanguageRepository;
import jobportal.models.offer_data_models.codebooks.OfferLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OfferLanguageService {
    @Autowired
    private OfferLanguageRepository offerLanguageRepository;

    public Collection<OfferLanguage> findAllOfferLanguages(){
        List<OfferLanguage> offerLanguages = new ArrayList<OfferLanguage>();
        for (OfferLanguage offerLanguage:offerLanguageRepository.findAll())
        {
            offerLanguages.add(offerLanguage);
        }
        return offerLanguages;
    }

    public OfferLanguage findOfferLanguageById(String id) {
        return offerLanguageRepository.findOfferLanguageById(id);
    }

    public void saveOfferLanguage(OfferLanguage ol){
        offerLanguageRepository.save(ol);
    }
}