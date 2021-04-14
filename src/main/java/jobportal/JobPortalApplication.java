package jobportal;

import jobportal.models.internal_models.cv_support.RelevanceScore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JobPortal SpringBoot application
 *
 * @author Jaroslav Jezek
 *
 */
@SpringBootApplication
public class JobPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplication.class, args);

        // Wake up the external predictions service
        RelevanceScore rs = new RelevanceScore();
        rs.wakeUp();
    }
}