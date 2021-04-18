package jobportal.ControllerTests;

import jobportal.JobPortalApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class UserControllerMVCTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test of showAdminFilteredUsersPageable() controller method
     * > tests if status is Ok
     * > tests if view name is appropriate
     * > tests if all model attributes exists
     * > tests if all unwanted attributes non-exists
     * > tests if selected model attributes has correct/expected values
     * > tests if model has no errors
     * > tests correct pagination - if firstUserNum is corresponding to 1st page with results
     */
    @Test
    public void testShowAdminFilteredUsersPageableWithPageNum1ControllerMethod() throws Exception {
        this.mockMvc.perform(get("/admin/searchedUsers/page/1", 1)
                .param("userId", "0")
                .param("emailSearch", "@seznam.cz")
                .param("fullNameSearch", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("adminAllUsers"))
                .andExpect(model().attributeExists
                        ("searchedEmail", "firstUserNum",
                                "lastUserNum", "totalUsers", "totalPages",
                                "regions", "relevanceScores", "registeredUsers",
                                "currentPage", "usersCount", "searching", "message_notification"))
                .andExpect(model().attributeDoesNotExist("searchedId", "searchedFullName"))
                .andExpect(model().attribute("firstUserNum", 1))
                .andExpect(model().attribute("searchedEmail", "@seznam.cz"))
                .andExpect(model().attribute("searching", true))
                .andExpect(model().attribute("message_notification", ""))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test of login() controller method with error received
     * > tests if status is Ok
     * > tests if view name is appropriate
     * > tests if error model attribute exists
     * > tests if all unwanted attributes non-exists
     * > tests if error model attribute has correct/expected value
     * > tests if model has no errors
     */
    @Test
    public void testLoginWithSomeErrorReceived() throws Exception {
        this.mockMvc.perform(get("/login", 1)
                .param("error", "Some error occurred"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeDoesNotExist("offers", "users", "searching"))
                .andExpect(model().attribute("error", "Zadaný přihlašovací email nebo heslo nejsou správné"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test of login() controller method with no errors received
     * > tests if status is Ok
     * > tests if view name is appropriate
     * > tests if all unwanted attributes non-exists
     * > tests if model has no errors
     */
    @Test
    public void testLoginWithNoErrorReceived() throws Exception {
        this.mockMvc.perform(get("/login", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error", "offers", "users", "searching"))
                .andExpect(model().hasNoErrors());
    }

    /**
     * Test of showRegistrationForm() controller method
     * > tests if status is Ok
     * > tests if view name is appropriate
     * > tests if all model attributes exists
     * > tests if all unwanted attributes non-exists
     * > tests if model has no errors
     */
    @Test
    public void testShowRegistrationFormMethod() throws Exception {
        this.mockMvc.perform(get("/registration", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("registeredUser", "regions",
                        "eduLevels", "eduGeneralFields"))
                .andExpect(model().attributeDoesNotExist("error", "offers", "users", "searching"))
                .andExpect(model().hasNoErrors());
    }
}
