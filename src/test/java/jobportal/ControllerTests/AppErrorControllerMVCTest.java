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
public class AppErrorControllerMVCTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test of handleError() controller method
     * > tests if status is Ok
     * > tests if view name is appropriate
     * > tests if all model attributes exists
     * > tests if all unwanted attributes non-exists
     * > tests if model has no errors
     */
    @Test
    public void testHandleErrorControllerMethod() throws Exception {

        this.mockMvc.perform(get("/error", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists
                        ("errorMsg", "errorCode"))
                .andExpect(model().attributeDoesNotExist("users", "offers", "totalPages"))
                .andExpect(model().hasNoErrors());
    }
}
