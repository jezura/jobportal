package jobportal.services;

import jobportal.dao.OfferRepository;
import jobportal.models.Field;
import jobportal.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public Collection<Offer> findAllOffers(){
        List<Offer> offers = new ArrayList<Offer>();
        for (Offer offer :offerRepository.findAll())
        {
            offers.add(offer);
        }
        return offers;
    }

    public Page<Offer> listAllOffers(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,20);
        return offerRepository.findAll(pageable);
    }

    public Collection<Offer> sortOffersAccToPredictions(Collection<Offer> offersToSort,
                 String codeField1, String codeField2, String codeField3, String codeField4, String codeField5){
        List<Offer> allOffers = new ArrayList(offersToSort);
        List<Offer> offers = new ArrayList();
        int j=15;
        for (int i = 0; (j > 0) && (i <= allOffers.size()-1); i++) {
            Offer o = allOffers.get(i);
            List<Field> fields = new ArrayList(o.getProfession().getFields());
            for (Field f : fields) {
                if(f.getCode().equals(codeField1)) {
                    if(!offers.contains(o)) {
                        offers.add(o);
                        j--;
                    }
                }
            }
        }

        j=10;
        for (int i = 0; (j > 0) && (i <= allOffers.size()-1); i++) {
            Offer o = allOffers.get(i);
            List<Field> fields = new ArrayList(o.getProfession().getFields());
            for (Field f : fields) {
                if(f.getCode().equals(codeField2)) {
                    if(!offers.contains(o)) {
                        offers.add(o);
                        j--;
                    }
                }
            }
        }

        j=5;
        for (int i = 0; (j > 0) && (i <= allOffers.size()-1); i++) {
            Offer o = allOffers.get(i);
            List<Field> fields = new ArrayList(o.getProfession().getFields());
            for (Field f : fields) {
                if(f.getCode().equals(codeField3)) {
                    if(!offers.contains(o)) {
                        offers.add(o);
                        j--;
                    }
                }
            }
        }

        j=3;
        for (int i = 0; (j > 0) && (i <= allOffers.size()-1); i++) {
            Offer o = allOffers.get(i);
            List<Field> fields = new ArrayList(o.getProfession().getFields());
            for (Field f : fields) {
                if(f.getCode().equals(codeField4)) {
                    if(!offers.contains(o)) {
                        offers.add(o);
                        j--;
                    }
                }
            }
        }

        j=2;
        for (int i = 0; (j > 0) && (i <= allOffers.size()-1); i++) {
            Offer o = allOffers.get(i);
            List<Field> fields = new ArrayList(o.getProfession().getFields());
            for (Field f : fields) {
                if(f.getCode().equals(codeField5)) {
                    if(!offers.contains(o)) {
                        offers.add(o);
                        j--;
                    }
                }
            }
        }
        return offers;
    }

    public Offer findOfferById(Long id) {
        return offerRepository.findOfferById(id);
    }

    public void saveOffer(Offer o){
        offerRepository.save(o);
    }
}