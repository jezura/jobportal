package jobportal.controllers;
import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.EduLog;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.models.internal_models.codebooks.Title;
import jobportal.services.CzechNameService;
import jobportal.services.TitleService;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Controller
public class CVController {
    @Autowired
    private CzechNameService czechNameService;
    @Autowired
    private TitleService titleService;
    @Autowired
    private EduLevelRepository eduLevelRepository;
    @Autowired
    private EduGeneralFieldRepository eduGeneralFieldRepository;

    @RequestMapping(value = "/demo")
    public String showDemoIndex() {
        return "demoIndex";
    }

    @RequestMapping(value = "/loadCvFile")
    public String showLoadCvFileForm() {
        return "loadCvFile";
    }

    @PostMapping(value = "/demo/processCv")
    public String processCV(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {
        EduLog eduLog = new EduLog();
        CVExtractor cvExtractor = new CVExtractor(eduLog, eduLevelRepository, eduGeneralFieldRepository);
        CVProfile cvProfile = new CVProfile();
        RelevanceScore relevanceScore = new RelevanceScore();
        cvExtractor.processCvAndSetTextContentToExtractedTextVariable(files);

        System.out.print(cvExtractor.getExtractedText());

        //       >>> EXTRACTING PROCESS <<<
        cvProfile.buildCompleteCvProfile(cvExtractor, czechNameService.findAllCzechNames(), titleService.findAllTitles());

        //      >>> LOG DO CONSOLE <<<
        System.out.println("");
        System.out.println("Extrahovane informace o uchazeci:");
        System.out.println("Jmeno: " + cvProfile.getFirstName());
        System.out.println("Prijmeni: " + cvProfile.getLastName());
        System.out.println("Pohlavi: " + cvProfile.getGender());
        if(cvProfile.getBirthDate() != null) {
            System.out.println("Datum narozeni: " + cvProfile.getBirthDate());
        }else if(cvProfile.getBirthYear() != 0) {
            System.out.println("Rok narozeni: " + cvProfile.getBirthYear());
        }
        if(cvProfile.getAge() != 0) {
            System.out.println("Vek: " + cvProfile.getAge() + " let");
        }
        System.out.print("Ziskane akademicke tituly: ");
        if(!cvProfile.getTitleList().isEmpty()) {
            for (Title title: cvProfile.getTitleList()) {
                System.out.print(" " + title.getOfficialVersion());
            }
        }else{
            System.out.print("bez titulu");
        }
        System.out.print("\n");
        System.out.println("Email: " + cvProfile.getEmail());
        System.out.println("Mobil: " + cvProfile.getMobile());
        System.out.println("Nejvyssi dosazeny stupen vzdelani: " + cvProfile.getMaxEducation().getMaxEduLvl().getEduLevel().getPrettyName());
        System.out.println("Obecny obor vzdelani: " + cvProfile.getMaxEducation().getEduGeneralField().getPrettyName());

        relevanceScore.getPredictions(cvProfile);


        System.out.println("Relevance scores:");

        for(int i=0;i<relevanceScore.getRelevanceScores().length;i++) {
            System.out.println(relevanceScore.getRelevanceScores()[i]);
        }

        model.addAttribute("cvProfile", cvProfile);
        model.addAttribute("relevanceScore", relevanceScore);
        model.addAttribute("eduLog", cvExtractor.getEduLog());

        return "demoExtractedCVProfile";
    }
}