package jobportal.controllers;

import jobportal.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * REST Controller responsible for providing selected progress data about offers - during new data load or data
 * deleting processes in administration environment.
 * OfferRestController is responsible also for selected offers data deleting.
 */
@RestController
public class OfferRestController {
    @Autowired
    private OfferService offerService;

    /**
     * Method returns count of all job offers stored in app database
     *
     * @return int - count of all job offers stored in app database
     */
    @GetMapping(value = "/admin/getOffersCount")
    public int getOffersCount() {
        return Long.valueOf(offerService.getCount()).intValue();
    }

    /**
     * Method delete all job offers from app database and returns number of deleted offers
     *
     * @return int - number of deleted offers
     */
    @GetMapping(value = "/admin/deleteAllOffers")
    public int deleteAllOffers() {
        int startOffersCount = Long.valueOf(offerService.getCount()).intValue();
        offerService.deleteAllOffers();
        return startOffersCount;
    }

    /**
     * Method delete all expired job offers from app database and returns number of deleted offers
     *
     * @return int - number of deleted offers
     */
    @GetMapping(value = "/admin/deleteAllExpiredOffers")
    public int deleteAllExpiredOffers() {
        return offerService.deleteAllExpiredOffers();
    }

    /**
     * Method delete all job offers oldest than given dates from app database and returns number of deleted offers
     *
     * @return int - number of deleted offers
     */
    @GetMapping(value = "/admin/deleteAllOffersBeforeGivenDates")
    public int deleteAllOffersBeforeGivenDates(
            @Param("oldestInsertionDate") String oldestInsertionDate,
            @Param("oldestEditDate") String oldestEditDate) {

        LocalDate localOldestInsertionDate;
        LocalDate localOldestEditDate;

        if (!oldestInsertionDate.isEmpty()) {
            localOldestInsertionDate = LocalDate.parse(oldestInsertionDate);
        } else {
            localOldestInsertionDate = null;
        }

        if (!oldestEditDate.isEmpty()) {
            localOldestEditDate = LocalDate.parse(oldestEditDate);
        } else {
            localOldestEditDate = null;
        }
        return offerService.deleteAllOffersBeforeGivenDates(localOldestInsertionDate, localOldestEditDate);
    }
}