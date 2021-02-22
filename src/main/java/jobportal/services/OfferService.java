package jobportal.services;

import jobportal.dao.OfferRepository;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public Collection<Offer> findAllOffers() {
        List<Offer> offers = new ArrayList<Offer>();
        for (Offer offer : offerRepository.findAll()) {
            offers.add(offer);
        }
        return offers;
    }

    public Page<Offer> listAllOffers(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return offerRepository.findAll(pageable);
    }

    public Collection<Offer> getOffersAccToPredictions(int pageNumber, RelevanceScore relevanceScore) {
        int limitField1 = 12;
        int offsetField1 = (pageNumber-1) * limitField1;
        int limitField2 = 9;
        int offsetField2 = (pageNumber-1) * limitField2;
        int limitField3 = 6;
        int offsetField3 = (pageNumber-1) * limitField3;
        int limitField4 = 3;
        int offsetField4 = (pageNumber-1) * limitField4;
        int limitField5 = 2;
        int offsetField5 = (pageNumber-1) * limitField5;

        return offerRepository.getOffersAccToPredictedRelevances(
                limitField1,
                limitField2,
                limitField3,
                limitField4,
                limitField5,
                offsetField1,
                offsetField2,
                offsetField3,
                offsetField4,
                offsetField5,
                relevanceScore.getFiveHighestRelevanceFieldsIds()[0],
                relevanceScore.getFiveHighestRelevanceFieldsIds()[1],
                relevanceScore.getFiveHighestRelevanceFieldsIds()[2],
                relevanceScore.getFiveHighestRelevanceFieldsIds()[3],
                relevanceScore.getFiveHighestRelevanceFieldsIds()[4]);
    }

    public long getCount() {
        return offerRepository.count();
    }

    public Page<Offer> findByTitleLikeAndFieldIdPageable(String title, String fieldId, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return offerRepository.findByTitleLikeAndFieldIdPageable(title, fieldId, pageable);
    }

    public Page<Offer> findByTitleContainingIgnoreCase(String title, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return offerRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Offer> findByFieldIdPageable(String fieldId, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return offerRepository.findByFieldIdPageable(fieldId, pageable);
    }

    public List<String> findTitlesLikeSearchTerm(String term){
        List<String> titles = offerRepository.findTitlesLikeSearchTerm(term);
        return titles;
    }

    public int deleteAllExpiredOffers() {
        LocalDate today = LocalDate.now().plusDays(2);
        return offerRepository.deleteByExpireDateBefore(today);
    }

    public int deleteAllOffersBeforeGivenDates(LocalDate oldestInsertionDate, LocalDate oldestEditDate) {
        if (oldestInsertionDate == null) {
            return offerRepository.deleteByEditDateBefore(oldestEditDate.plusDays(1));
        } else if (oldestEditDate == null) {
            return offerRepository.deleteByInsertionDateBefore(oldestInsertionDate.plusDays(1));
        } else {
            return offerRepository.deleteByInsertionDateBeforeAndEditDateBefore(oldestInsertionDate.plusDays(1), oldestEditDate.plusDays(1));
        }
    }

    public void deleteAllOffers() {
        offerRepository.deleteAll();
    }

    public Offer findOfferById(Long id) {
        return offerRepository.findOfferById(id);
    }

    public void saveOffer(Offer o) {
        offerRepository.save(o);
    }

    public void deleteOffer(Offer o) {
        offerRepository.delete(o);
    }
}