package jobportal.controllers;

import jobportal.models.Field;
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
    private CVProfile cvProfile;
    private RelevanceScore relevanceScore;

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

    @RequestMapping(value = "/personalizedOffers", method = RequestMethod.POST)
    public String extractCVInfoAndPredictRelevances(
            Model model,
            @RequestParam("file") MultipartFile[] files) throws IOException {
        cvProfile = new CVProfile();
        relevanceScore = new RelevanceScore();
        EduLog eduLog = new EduLog();
        CVExtractor cvExtractor = new CVExtractor(eduLog);
        cvExtractor.processCvAndSetTextContentToExtractedTextVariable(files);

        //       >>> EXTRACTING PROCESS <<<
        cvProfile.buildCompleteCvProfile(cvExtractor, czechNameService.findAllCzechNames(), titleService.findAllTitles());
        relevanceScore.getPredictions(cvProfile);

        return showIndexWithPersonalizedOffersPageable(model, 1);
    }

    @RequestMapping(value = "/personalizedOffers/page/{pageNumber}")
    public String showIndexWithPersonalizedOffersPageable(
            Model model, @PathVariable("pageNumber") int currentPage) {

        Collection<Offer> offers = offerService.getOffersAccToPredictions(
                currentPage, relevanceScore);
        model.addAttribute("offers", offers);
        model.addAttribute("cvProfile", cvProfile);
        model.addAttribute("relevanceScore", relevanceScore);
        return "index";
    }
}