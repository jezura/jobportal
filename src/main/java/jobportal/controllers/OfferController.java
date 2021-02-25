package jobportal.controllers;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.EduLog;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.models.internal_models.security.MyUser;
import jobportal.models.offer_data_models.Offer;
import jobportal.services.CzechNameService;
import jobportal.services.OfferService;
import jobportal.services.PersonService;
import jobportal.services.TitleService;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Controller
public class OfferController {
    private CVProfile cvProfile;
    private RelevanceScore relevanceScore;

    @Autowired
    private OfferService offerService;
    @Autowired
    private TitleService titleService;
    @Autowired
    private PersonService personService;
    @Autowired
    private CzechNameService czechNameService;
    @Autowired
    private EduLevelRepository eduLevelRepository;
    @Autowired
    private EduGeneralFieldRepository eduGeneralFieldRepository;


    @RequestMapping(value = "/")
    public String showIndexWithAllOffers(Model model) {
        return showAllOffersPageable(model, 1);
    }

    @GetMapping(value = "/page/{pageNumber}")
    public String showAllOffersPageable(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Offer> offerPage = offerService.findAllOffersPageable(currentPage, 20);
        Collection<Offer> offers = offerPage.getContent();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser;
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);
        model.addAttribute("user", registeredUser);
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
        CVExtractor cvExtractor = new CVExtractor(eduLog, eduLevelRepository, eduGeneralFieldRepository);
        cvExtractor.processCvAndSetTextContentToExtractedTextVariable(files);

        //       >>> EXTRACTING PROCESS <<<
        cvProfile.buildCompleteCvProfile(cvExtractor, czechNameService.findAllCzechNames(), titleService.findAllTitles());
        relevanceScore.getPredictions(cvProfile);

        return showIndexWithPersonalizedOffersPageable(model, 1);
    }

    @RequestMapping(value = "/personalizedOffers/user/{userId}")
    public String predictRelevancesFromUserData(
            Model model,
            @PathVariable("userId") int userId) {
        cvProfile = null;
        RegisteredUser user = personService.findRegisteredUserById(userId);
        relevanceScore = new RelevanceScore(user.getFieldsRelevancy().getRelevanceScoresArray());
        return showIndexWithPersonalizedOffersPageable(model, 1);
    }

    @RequestMapping(value = "/personalizedOffers/page/{pageNumber}")
    public String showIndexWithPersonalizedOffersPageable(
            Model model, @PathVariable("pageNumber") int currentPage) {

        Collection<Offer> offers = offerService.getOffersAccToPredictions(
                currentPage, relevanceScore);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser;
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);
        model.addAttribute("user", registeredUser);
        model.addAttribute("offers", offers);
        model.addAttribute("cvProfile", cvProfile);
        model.addAttribute("relevanceScore", relevanceScore);
        return "index";
    }
}