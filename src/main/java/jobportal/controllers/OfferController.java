package jobportal.controllers;

import jobportal.models.Offer;
import jobportal.models.cv_support.*;
import jobportal.services.CzechNameService;
import jobportal.services.OfferService;
import jobportal.services.TitleService;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@Controller
public class OfferController {
    @Autowired
    private OfferService offerService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private CzechNameService czechNameService;

    @RequestMapping(value = "/")
    public String showIndexWithAllOffers(Model model) {
        return showAllOffersPageable(model, 1);
    }

    @GetMapping(value = "/page/{pageNumber}")
    public String showAllOffersPageable(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Offer> offerPage = offerService.listAllOffers(currentPage, 20);
        Collection<Offer> offers = offerPage.getContent();
        model.addAttribute("offers", offers);
        return "index";
    }

    @RequestMapping(value = "/personalizedOffers")
    public String showIndexWithPersonalizedOffers(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {
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

        relevanceScore.getPredictions(cvProfile);
        int [] fiveHighest = relevanceScore.getFiveHighest();

        Page<Offer> offerPage = offerService.listAllOffers(1, 600);
        Collection<Offer> offers = offerPage.getContent();

        offers = offerService.sortOffersAccToPredictions(offers, String.valueOf(fiveHighest[0]),
                String.valueOf(fiveHighest[1]), String.valueOf(fiveHighest[2]), String.valueOf(fiveHighest[3]),
                String.valueOf(fiveHighest[4]));

        model.addAttribute("offers", offers);
        model.addAttribute("cvProfile", cvProfile);
        model.addAttribute("relevanceScore", relevanceScore);
        return "index";
    }


}