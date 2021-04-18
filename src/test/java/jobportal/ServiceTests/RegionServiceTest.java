package jobportal.ServiceTests;

import jobportal.JobPortalApplication;
import jobportal.dao.RegionRepository;
import jobportal.models.offer_data_models.codebooks.Region;
import jobportal.services.RegionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionRepository regionRepository;

   /**
     * Test of repository find function
     * Tests if service will return excepted region from DB
     */
    @Test
    public void testFindRegionByIdMethod(){
        Region region = regionService.findRegionById("Kraj/86");
        assertEquals(region.getName(), "Královéhradecký kraj");
    }

    /**
     * Test of saving new Region to DB and also repository deleting selected region
     * Tests if service will successfully save newly created Region and if repository successfully delete
     * selected Region from DB
     */
    @Test
    public void testSaveNewRegionAndDeleteRegionRepoMethod(){
        Region region = new Region();
        region.setId("novyRegionTest");
        region.setKodNuts3("nejakyKodNuts1265");
        region.setCode("nejakyKod123");
        region.setName("NovyCeskyRegion");

        regionService.saveRegion(region);

        Region novyRegionZDB = regionService.findRegionById("novyRegionTest");

        assertEquals(region.getName(), novyRegionZDB.getName());

        long countBeforeDelete = regionService.getCount();
        regionRepository.delete(novyRegionZDB);
        long countAfterDelete = regionService.getCount();

        assertTrue(countBeforeDelete > countAfterDelete);
    }
}
