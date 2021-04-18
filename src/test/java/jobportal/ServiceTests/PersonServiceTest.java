package jobportal.ServiceTests;

import jobportal.JobPortalApplication;
import jobportal.models.internal_models.data_entites.user.Administrator;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.services.PersonService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionSystemException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class PersonServiceTest {
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
    private PersonService personService;

    /**
     * Model attribute validation test
     * Tests if wrong first name set to RegisteredUser causes Constraints Violation
     */
    @Test
    public void testRegisteredUserBadFirstNameFormatConstraintsViolation(){
        RegisteredUser registeredUser = new RegisteredUser();
        String unvalidFirstName = "Jaroslav Ježek";
        registeredUser.setFirstName(unvalidFirstName);

        Set<ConstraintViolation<RegisteredUser>> violations = validator.validate(registeredUser);
        boolean firstNameViolation = false;
        for(ConstraintViolation<RegisteredUser> v : violations){
            if(v.getMessage().equals("Neplatné křestní jméno, povolená délka 3-20 znaků")){
                firstNameViolation = true;
            }
        }
        assertTrue(firstNameViolation);
    }

    /**
     * Model attribute validation test
     * Tests if wrong email set to RegisteredUser causes Constraints Violation
     */
    @Test
    public void testRegisteredUserBadEmailFormatConstraintsViolation(){
        RegisteredUser registeredUser = new RegisteredUser();
        String unvalidEmail = "jaroslav.jezek.cz";
        registeredUser.setEmail(unvalidEmail);

        Set<ConstraintViolation<RegisteredUser>> violations = validator.validate(registeredUser);
        boolean emailViolation = false;
        for(ConstraintViolation<RegisteredUser> v : violations){
            if(v.getMessage().equals("Neplatný email/login. Zadejte prosím email ve validním formátu")){
                emailViolation = true;
            }
        }
        assertTrue(emailViolation);
    }

    /**
     * Tests if NULL attributes (Constraints Violation) does not allow the entity to be saved/persist to the database
     */
    @Test(expected = TransactionSystemException.class)
    public void testSaveRegisteredUserNullAttributesConstraintsViolation(){
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(123456);
        personService.saveRegisteredUser(registeredUser);

    }

    /**
     * Tests findAdministratorByEmail() method
     */
    @Test
    public void testFindAdministratorByEmail(){
        Administrator admin = personService.findAdministratorByEmail("administrator@jobportal.cz");
        assertNotNull(admin);
    }

    /**
     * Tests findEmailsLikeSearchTerm() method - for AJAX suggest/autocomplete
     */
    @Test
    public void testFindEmailsLikeSearchTerm(){
        List<String> suggestedEmails = personService.findEmailsLikeSearchTerm("@");
        assertFalse(suggestedEmails.isEmpty());
    }


    /**
     * Tests deleting of non existing RegisteredUser entity from DB
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteNonExistRegisteredUser(){
        personService.deleteRegisteredUser(123987);
    }
}
