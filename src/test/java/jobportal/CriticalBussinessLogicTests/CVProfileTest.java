package jobportal.CriticalBussinessLogicTests;

import jobportal.JobPortalApplication;
import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.Title;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.MaxEduLvl;
import jobportal.models.internal_models.cv_support.MaxEducation;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class CVProfileTest {
    @Autowired
    EduGeneralFieldRepository eduGeneralFieldRepository;
    @Autowired
    EduLevelRepository eduLevelRepository;

    /**
     * Quick registration intelligent function prefill extracted information to RegisteredUser instance
     * Tests if preFillRegisteredUserAttributesWithIdentifiedInformation() works properly as expected
     * Tests if all extracted information in CVProfile are correctly prefilled to given RegisteredUser
     * instance attributes
     */
    @Test
    public void testPreFillRegisteredUserAttributesWithIdentifiedInformation() {
        RegisteredUser registeredUser = new RegisteredUser();
        CVProfile cvProfile = returnSampleTestingCVProfileInstance();
        cvProfile.preFillRegisteredUserAttributesWithIdentifiedInformation
                (registeredUser, eduLevelRepository, eduGeneralFieldRepository);

        assertEquals(registeredUser.getGender(), cvProfile.getGender());
        assertEquals(registeredUser.getEmail(), cvProfile.getEmail());
        assertEquals(registeredUser.getFirstName(), cvProfile.getFirstName());
        assertEquals(registeredUser.getLastName(), cvProfile.getLastName());
        assertEquals(registeredUser.getBirthYear(), cvProfile.getBirthDate().getYear());
        assertEquals(registeredUser.getEduLevel().getPrettyName(), cvProfile.getMaxEducation().getMaxEduLvl().getEduLevel().getPrettyName());
        assertEquals(registeredUser.getEduGeneralField().getPrettyName(),
                cvProfile.getMaxEducation().getEduGeneralField().getPrettyName());

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
