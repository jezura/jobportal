package jobportal.CriticalBussinessLogicTests;

import jobportal.JobPortalApplication;
import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.Title;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.MaxEduLvl;
import jobportal.models.internal_models.cv_support.MaxEducation;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class RelevanceScoreTest {
    @Autowired
    EduGeneralFieldRepository eduGeneralFieldRepository;
    @Autowired
    EduLevelRepository eduLevelRepository;

    /**
     * Tests getPercentRelevanceScore() method
     * Tests if this method correctly converts float relevance value to expected String percent value
     */
    @Test
    public void testFindTitlesLikeSearchTerm(){
        RelevanceScore relevanceScore = new RelevanceScore(returnSampleTestingRelevancesArray());
        assertEquals(relevanceScore.getPercentRelevanceScore(3), "55,00 %");
    }

    /**
     * Tests getFiveHighestRelevanceFieldsIds() method
     * Tests if this method returns expected job field IDs (IDs of five job fields which has top predicted relevance)
     */
    @Test
    public void testGetFiveHighestRelevanceFieldsIdsMethod(){
        RelevanceScore relevanceScore = new RelevanceScore(returnSampleTestingRelevancesArray());
        assertArrayEquals(relevanceScore.getFiveHighestRelevanceFieldsIds(),
                new String[]{"OborCinnostiProVm/4", "OborCinnostiProVm/15",
                        "OborCinnostiProVm/6", "OborCinnostiProVm/8", "OborCinnostiProVm/10"});
    }

    /**
     * Prediction external web service communication test
     * Tests if getPredictions() method is passed correctly and if predicted job field relevance was correctly
     * received from external Python/Flask prediction service with neural network model
     */
    @Test
    public void testGetPredictionsMethod() throws IOException {
        RelevanceScore relevanceScore = new RelevanceScore(returnSampleTestingRelevancesArray());

        assertTrue(relevanceScore.getPredictions(returnSampleTestingCVProfileInstance()));

        assertTrue(relevanceScore.getRelevanceScores().length > 0);
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

    /**
     * Method returns new CVProfile instance with testing data included -  for testing
     * @return CVProfile instance
     */
    public CVProfile returnSampleTestingCVProfileInstance(){
        CVProfile cvProfile = new CVProfile();
        cvProfile.setAge(25);
        cvProfile.setBirthDate(LocalDate.now().minusYears(25));
        cvProfile.setEmail("exampleaddress@seznam.cz");
        cvProfile.setFirstName("Jaroslav");
        cvProfile.setLastName("Ježek");
        cvProfile.setGender("muž");
        cvProfile.setMobile("+420 616161616");

        List<Title> titles = new ArrayList<>();
        Title title = new Title();
        title.setId(1313131);
        title.setDegree("Vysokoskolske_bakalarske");
        title.setOfficialVersion("Bc");
        title.setTitleVariant("Bc");
        titles.add(title);

        cvProfile.setTitleList(titles);

        MaxEduLvl maxEduLvl = new MaxEduLvl(eduLevelRepository);
        maxEduLvl.setEduLevel("Vysokoskolske_bakalarske");

        MaxEducation maxEducation = new MaxEducation(eduGeneralFieldRepository);
        maxEducation.setMaxEduLvl(maxEduLvl);
        maxEducation.setEduGeneralField("Informatika_a_management");

        cvProfile.setMaxEducation(maxEducation);

        return cvProfile;
    }
}
