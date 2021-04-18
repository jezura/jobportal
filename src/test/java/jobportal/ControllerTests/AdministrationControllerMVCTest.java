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
public class AdministrationControllerMVCTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test of showAdminAllOffersPageable() controller method
     * > tests if status is Ok
     * > tests if view name is appropriate
     * > tests if all model attributes exists
     * > tests if unwanted attributes non-exists
     * > tests if model has no errors
     * > tests correct pagination - if lastOfferNum is corresponding to 2nd page with results ( for 20 offers per page)
     */
    @Test
    public void testShowAdminAllOffersPageableWithPageNum2ControllerMethod() throws Exception {
        this.mockMvc.perform(get("/admin/allOffers/page/2", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/adminAllOffers"))
                .andExpect(model().attributeExists
                        ("offers", "fields", "totalOffers",
                                "totalPages", "firstOfferNum", "lastOfferNum",
                                "message_notification", "totalPages"))
                .andExpect(model().attributeDoesNotExist("searching"))
                .andExpect(model().attribute("lastOfferNum", 40))
                .andExpect(model().attribute("message_notification", ""))
                .andExpect(model().hasNoErrors());
    }
}
