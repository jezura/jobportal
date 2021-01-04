package jobportal.controllers;
import jobportal.models.cv_support.*;
import jobportal.services.CzechNameService;
import jobportal.services.TitleService;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class CVController {
    @Autowired
    private CzechNameService czechNameService;
    @Autowired
    private TitleService titleService;

    @RequestMapping(value = "/demo")
    public String showDemoIndex() {
        return "demoIndex";
    }

    @RequestMapping(value = "/loadCvFile")
    public String showLoadCvFileForm() {
        return "loadCvFile";
    }

    @RequestMapping(value = "/demo/processCv", method = RequestMethod.POST)
    public String processCV(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {
        EduLog eduLog = new EduLog();
        CVExtractor cvExtractor = new CVExtractor(eduLog);
        CVProfile cvProfile = new CVProfile();
        RelevanceScore relevanceScore = new RelevanceScore();

        String fileName = files[0].getOriginalFilename();
        //Path fileNameAndPath = Paths.get("D:\\",fileName);
        Path fileNameAndPath = Paths.get(new ClassPathResource("filename").getPath());
        try {
            Files.write(fileNameAndPath,files[0].getBytes());
        } catch (IOException e) {
                e.printStackTrace();
        }

        //File savedFile = new File("D:\\" + files[0].getOriginalFilename());
        File savedFile = new File(String.valueOf(fileNameAndPath));

        String extractedText = cvExtractor.getCvTextData(savedFile, fileName);
        System.out.print(extractedText);

        //Deleting of saved file
        if(savedFile.delete()) {
            System.out.println("Saved CV file was immediately deleted after all text extracted.");
        } else {
            System.out.println("NOT POSSIBLE TO DELETE SAVED FILE");
        }

        //       >>> EXTRACTING PROCESS <<<

        CzechName extractedFirstName = cvExtractor.extractFirstName(extractedText,
                czechNameService.findAllCzechNames(), 40);
        cvProfile.setFirstName(extractedFirstName.getName());
        cvProfile.setGender(extractedFirstName.getGender());
        cvProfile.setLastName(cvExtractor.extractLastName(extractedText, extractedFirstName.getName()));
        cvProfile.setTitleList(cvExtractor.extractTitle(extractedText, titleService.findAllTitles()));
        cvProfile.setEmail(cvExtractor.extractEmail(extractedText));
        cvProfile.setMobile(cvExtractor.extractMobile(extractedText));
        cvProfile.setMaxEducation(cvExtractor.extractMaxEducationAndGeneralEduField(extractedText, cvProfile.getTitleList()));

        // extract and set age and birthDate or birthYear
        LocalDate extractedBirthDate = cvExtractor.extractBirthDate(extractedText);
        if (extractedBirthDate != null) {
            long years;
            if(extractedBirthDate.getYear() < 1950)
            {
                extractedBirthDate = extractedBirthDate.plusYears(100);
                cvProfile.setBirthYear(extractedBirthDate.getYear());
            }else{
                cvProfile.setBirthDate(extractedBirthDate);
            }
            years = ChronoUnit.YEARS.between(extractedBirthDate, LocalDate.now());
            cvProfile.setAge((int) years);
        }

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
        System.out.println("Nejvyssi dosazeny stupen vzdelani: " + cvProfile.getMaxEducation().getMaxEduLvl().getMaxEduLvlName());
        System.out.println("Obecny obor vzdelani: " + cvProfile.getMaxEducation().getGeneralEduField());

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