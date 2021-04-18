package jobportal.ServiceTests;

import jobportal.JobPortalApplication;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.codebooks.Field;
import jobportal.models.offer_data_models.codebooks.Profession;
import jobportal.services.OfferService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionSystemException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class OfferServiceTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Autowired
    private OfferService offerService;

   /**
     * Test of Personalization offer sorter (getting offers from database according to predicted job field relevance)
     * Tests if service will return non empty collection of recommended offers
     */
    @Test
    public void testPersonalizationGetOffersAccToPredictionsMethod(){
        RelevanceScore relevanceScore = new RelevanceScore();
        relevanceScore.setRelevanceScores(returnSampleTestingRelevancesArray());
        Collection<Offer> offers = offerService.getOffersAccToPredictions(1, relevanceScore);
        assertFalse(offers.isEmpty());
    }

    /**
     * Test of Personalization offer sorter (getting offers from database according to predicted job field relevance)
     * Test of technical accuracy of returned data
     * Tests if collection of recommended offers has valid content (the test passes only if the collection does not
     * contain offers with zero value of the predicted relevance score and at the same time contains at least one offer
     * with the highest value of relevance score)
     */
    @Transactional
    @Test
    public void testPersonalizationGetOffersAccToPredictionsDataCorrectness(){
        RelevanceScore relevanceScore = new RelevanceScore();
        relevanceScore.setRelevanceScores(returnSampleTestingRelevancesArray());
        Collection<Offer> offers = offerService.getOffersAccToPredictions(1, relevanceScore);

        boolean noOffersWithJobFieldNum3 = true;
        for(Offer o : offers){
            for(Field f : o.getProfession().getFields()){
                if (f.getId().equals("OborCinnostiProVm/3")) {
                    noOffersWithJobFieldNum3 = false;
                    break;
                }
            }
        }

        boolean containsOffersWithJobFieldNum4 = false;
        for(Offer o : offers){
            for(Field f : o.getProfession().getFields()){
                if (f.getId().equals("OborCinnostiProVm/4")) {
                    containsOffersWithJobFieldNum4 = true;
                    break;
                }
            }
        }

        assertTrue(noOffersWithJobFieldNum3);
        assertTrue(containsOffersWithJobFieldNum4);
    }

    /**
     * Model attribute validation test
     * Tests if null profession instance set to Offer causes Constraints Violation
     */
    @Test
    public void testOfferNullProfessionSetConstraintsViolation(){
        Offer offer = new Offer();
        Profession p = null;
        offer.setId(Long.valueOf("888888888888"));
        offer.setProfession(p);

        Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
        boolean professionViolation = false;
        for(ConstraintViolation<Offer> v : violations){
            if(v.getPropertyPath().toString().equals("profession")){
                professionViolation = true;
            }
        }
        assertTrue(professionViolation);
    }

    /**
     * Tests if NULL attributes (Constraints Violation) does not allow the entity to be saved/persist to the database
     */
    @Test(expected = TransactionSystemException.class)
    public void testSaveOfferNullAttributesConstraintsViolation(){
        Offer offer = new Offer();
        offer.setId(Long.valueOf("99999999999999"));
        offerService.saveOffer(offer);

    }

    /**
     * Tests findTitlesLikeSearchTerm() method - for AJAX suggest/autocomplete
     */
    @Test
    public void testFindTitlesLikeSearchTerm(){
        List<String> suggestedOfferTitles = offerService.findTitlesLikeSearchTerm("administrativ");
        assertFalse(suggestedOfferTitles.isEmpty());
    }

    /**
     * Method returns array of 15 relevance scores for testing
     * @return float[] - sampleTestingRelevnces
     */
    public float[] returnSampleTestingRelevancesArray(){
        return new float[]{(float) 0.01, (float) 0.02, (float) 0.00, (float) 0.55, (float) 0.11,
                (float) 0.015, (float) 0.09, (float) 0.05, (float) 0.075, (float) 0.002,
                (float) 0.01, (float) 0.00, (float) 0.06, (float) 0.33, (float) 0.00};
    }
}
