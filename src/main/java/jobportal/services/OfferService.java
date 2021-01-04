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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Page<Offer> listAllOffers(int pageNumber, int size){
        Pageable pageable = PageRequest.of(pageNumber-1,size);
        return offerRepository.findAll(pageable);
    }

    public Collection<Offer> sortOffersAccToPredictions(Collection<Offer> offersToSort,
                 String codeField1, String codeField2, String codeField3, String codeField4, String codeField5){

        System.out.println("Now!!!!!!! " + System.currentTimeMillis());
        List<Offer> allOffers = new ArrayList(offersToSort);
        List<Offer> offers1 = new ArrayList();
        List<Offer> offers2 = new ArrayList();
        List<Offer> offers3 = new ArrayList();
        List<Offer> offers4 = new ArrayList();
        List<Offer> offers5 = new ArrayList();

        int iter=0;
        int j=10;
        int k=6;
        int l=4;
        int m=3;
        int n=2;

        while ((iter < allOffers.size()) && !((j==0) && (k==0) && (l==0) && (m==0) && (n==0))) {
            Offer o = allOffers.get(iter);
            List<Field> fields = new ArrayList(o.getProfession().getFields());
            for (Field f : fields) {
                if((f.getCode().equals(codeField1)) && (j > 0)) {
                    if((!offers1.contains(o)) && (!offers2.contains(o)) && (!offers3.contains(o)) &&
                            (!offers4.contains(o)) && (!offers5.contains(o))) {
                        offers1.add(o);
                        j--;
                    }
                }else if((f.getCode().equals(codeField2)) && (k > 0)) {
                    if((!offers1.contains(o)) && (!offers2.contains(o)) && (!offers3.contains(o)) &&
                            (!offers4.contains(o)) && (!offers5.contains(o))) {
                        offers2.add(o);
                        k--;
                    }
                }else if((f.getCode().equals(codeField3)) && (l > 0)) {
                    if((!offers1.contains(o)) && (!offers2.contains(o)) && (!offers3.contains(o)) &&
                            (!offers4.contains(o)) && (!offers5.contains(o))) {
                        offers3.add(o);
                        l--;
                    }
                }else if((f.getCode().equals(codeField4) ) && (m > 0)) {
                    if((!offers1.contains(o)) && (!offers2.contains(o)) && (!offers3.contains(o)) &&
                            (!offers4.contains(o)) && (!offers5.contains(o))) {
                        offers4.add(o);
                        m--;
                    }
                }else if((f.getCode().equals(codeField5) ) && (n > 0)) {
                    if((!offers1.contains(o)) && (!offers2.contains(o)) && (!offers3.contains(o)) &&
                            (!offers4.contains(o)) && (!offers5.contains(o))) {
                        offers5.add(o);
                        n--;
                    }
                }
            }
            iter++;
        }

        List<Offer> sortedOffers = Stream.of(offers1, offers2, offers3, offers4, offers5)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        System.out.println("End!!!!!!! " + System.currentTimeMillis());

        return sortedOffers;



        /*List<Offer> allOffers = new ArrayList(offersToSort);
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
        return offers;*/
    }

    public Offer findOfferById(Long id) {
        return offerRepository.findOfferById(id);
    }

    public void saveOffer(Offer o){
        offerRepository.save(o);
    }
}