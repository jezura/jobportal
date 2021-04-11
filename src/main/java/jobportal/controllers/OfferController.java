package jobportal.controllers;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.EduLog;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.codebooks.*;
import jobportal.services.*;
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
import java.time.LocalDate;
import java.util.*;

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
    private RegionService regionService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private OfferSkillService offerSkillService;
    @Autowired
    private SkillService skillService;
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
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);
        Collection<Region> regions = regionService.findAllRegions();
        Collection<District> districts = districtService.findAllDistricts();
        Collection<Field> fields = fieldService.findAllFields();

        int todayDay = LocalDate.now().getDayOfYear();

        long totalOffers = offerPage.getTotalElements();
        int totalPages = offerPage.getTotalPages();
        int lastOfferNum = currentPage * 20;
        int firstOfferNum = lastOfferNum - 20 + 1;
        if ((long) lastOfferNum > totalOffers) {
            lastOfferNum = (int) totalOffers;
        }
        model.addAttribute("firstOfferNum", firstOfferNum);
        model.addAttribute("lastOfferNum", lastOfferNum);
        model.addAttribute("totalOffers", totalOffers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        model.addAttribute("todayDay", todayDay);

        model.addAttribute("user", registeredUser);
        model.addAttribute("offers", offers);
        model.addAttribute("regions", regions);
        model.addAttribute("districts", districts);
        model.addAttribute("fields", fields);
        return "index";
    }

    @GetMapping({"/searchOffers"})
    public String showFilteredOffers(Model model, @RequestParam(name = "locationSearch", required = false) String locationSearch,
                                     @RequestParam(name = "fieldSearch", required = false) String fieldSearch,
                                     @RequestParam(name = "titleAndEmployerSearch", required = false) String titleAndEmployerSearch,
                                     @RequestParam(name = "page", required = false) String page) {

        int currentPage;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        } else {
            currentPage = 1;
        }

        System.out.println("Location search: " + locationSearch);
        System.out.println("Field search: " + fieldSearch);
        System.out.println("titleAndEmployer search: " + titleAndEmployerSearch);

        if ((locationSearch.equals("all")) && (fieldSearch.equals("all")) && (titleAndEmployerSearch.isBlank())) {
            return this.showAllOffersPageable(model, 1);
        } else {
            return showFilteredOffersPageable(model, currentPage, locationSearch, fieldSearch, titleAndEmployerSearch);
        }
    }

    @GetMapping({"/searchedOffers/page/{pageNumber}"})
    public String showFilteredOffersPageable(Model model, @PathVariable("pageNumber") int currentPage, String locationSearch, String fieldSearch, String titleAndEmployerSearch) {
        List<Offer> offers = new ArrayList();
        Page<Offer> offerPage;

        if ((!locationSearch.equals("all")) && (!fieldSearch.equals("all")) && (!titleAndEmployerSearch.isBlank())) {
            if (locationSearch.contains("Kraj")) {
                offerPage = offerService.findByRegionIdAndFieldIdAndTitleLikeOrEmployerLikePageable(locationSearch, fieldSearch, titleAndEmployerSearch, currentPage, 20);
            } else {
                offerPage = offerService.findByDistrictIdAndFieldIdAndTitleLikeOrEmployerLikePageable(locationSearch, fieldSearch, titleAndEmployerSearch, currentPage, 20);
            }
            addSearchedLocation(model, locationSearch);
            addSearchedField(model, fieldSearch);
            model.addAttribute("searchedTitleAndEmployer", titleAndEmployerSearch);
        } else if ((!locationSearch.equals("all")) && (!fieldSearch.equals("all"))) {
            if (locationSearch.contains("Kraj")) {
                offerPage = offerService.findByRegionIdAndFieldIdPageable(locationSearch, fieldSearch, currentPage, 20);
            } else {
                offerPage = offerService.findByDistrictIdAndFieldIdPageable(locationSearch, fieldSearch, currentPage, 20);
            }
            addSearchedLocation(model, locationSearch);
            addSearchedField(model, fieldSearch);
        } else if ((!locationSearch.equals("all")) && (!titleAndEmployerSearch.isBlank())) {
            if (locationSearch.contains("Kraj")) {
                offerPage = offerService.findByRegionIdAndTitleLikeOrEmployerLikePageable(locationSearch, titleAndEmployerSearch, currentPage, 20);
            } else {
                offerPage = offerService.findByDistrictIdAndTitleLikeOrEmployerLikePageable(locationSearch, titleAndEmployerSearch, currentPage, 20);
            }
            addSearchedLocation(model, locationSearch);
            model.addAttribute("searchedTitleAndEmployer", titleAndEmployerSearch);
        } else if ((!fieldSearch.equals("all")) && (!titleAndEmployerSearch.isBlank())) {
            offerPage = offerService.findByFieldIdAndTitleLikeOrEmployerLikePageable(fieldSearch, titleAndEmployerSearch, currentPage, 20);
            addSearchedField(model, fieldSearch);
            model.addAttribute("searchedTitleAndEmployer", titleAndEmployerSearch);
        } else if (!locationSearch.equals("all")) {
            if (locationSearch.contains("Kraj")) {
                offerPage = offerService.findByRegionIdPageable(locationSearch, currentPage, 20);
            } else {
                offerPage = offerService.findByDistrictIdPageable(locationSearch, currentPage, 20);
            }
            addSearchedLocation(model, locationSearch);
            addSearchedField(model, fieldSearch);
        } else if (!fieldSearch.equals("all")) {
            offerPage = offerService.findByFieldIdPageable(fieldSearch, currentPage, 20);
            addSearchedField(model, fieldSearch);
            addSearchedLocation(model, locationSearch);
        } else {
            offerPage = offerService.findByTitleLikeOrEmployerLikePageable(titleAndEmployerSearch, currentPage, 20);
            model.addAttribute("searchedTitleAndEmployer", titleAndEmployerSearch);
            addSearchedLocation(model, locationSearch);
            addSearchedField(model, fieldSearch);
        }

        long totalOffers = offerPage.getTotalElements();
        int totalPages = offerPage.getTotalPages();
        int lastOfferNum = currentPage * 20;
        int firstOfferNum = lastOfferNum - 20 + 1;
        if ((long) lastOfferNum > totalOffers) {
            lastOfferNum = (int) totalOffers;
        }

        offers = offerPage.getContent();
        model.addAttribute("firstOfferNum", firstOfferNum);
        model.addAttribute("lastOfferNum", lastOfferNum);
        model.addAttribute("totalOffers", totalOffers);
        model.addAttribute("totalPages", totalPages);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser;
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);

        System.out.println("Offers list size: " + offers.size());
        Collection<Region> regions = regionService.findAllRegions();
        Collection<District> districts = districtService.findAllDistricts();
        Collection<Field> fields = fieldService.findAllFields();

        int todayDay = LocalDate.now().getDayOfYear();
        model.addAttribute("regions", regions);
        model.addAttribute("districts", districts);
        model.addAttribute("fields", fields);
        model.addAttribute("offers", offers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("user", registeredUser);
        model.addAttribute("todayDay", todayDay);
        model.addAttribute("searching", true);
        return "index";
    }

    @RequestMapping(value = "/titlesAndEmployersAutocomplete")
    @ResponseBody
    public List<String> autoTitleAndEmployer(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> titlesAndEmployers = offerService.findTitlesAndEmployersLikeSearchTerm(term);
        return titlesAndEmployers;
    }

    @RequestMapping(value = "/personalizedOffers", method = RequestMethod.POST)
    public String extractCVInfoAndPredictRelevances(
            Model model,
            @RequestParam(value = "switchUseLocalization", required = false) String switchUseLocalization,
            @RequestParam(value = "location", required = false, defaultValue = "") String regionId,
            @RequestParam("file") MultipartFile[] files) throws IOException {

        cvProfile = new CVProfile();
        relevanceScore = new RelevanceScore();
        EduLog eduLog = new EduLog();
        CVExtractor cvExtractor = new CVExtractor(eduLog, eduLevelRepository, eduGeneralFieldRepository);
        cvExtractor.processCvAndSetTextContentToExtractedTextVariable(files);

        //       >>> EXTRACTING PROCESS <<<
        cvProfile.buildCompleteCvProfile(cvExtractor, czechNameService.findAllCzechNames(), titleService.findAllTitles());
        relevanceScore.getPredictions(cvProfile);

        if (switchUseLocalization != null) {
            if ((switchUseLocalization.equals("isChecked")) && (!regionId.isBlank())) {
                return showIndexWithPersonalizedOffersPageable(model, 1, regionId.replaceAll("/", "-"));
            }
        }
        return showIndexWithPersonalizedOffersPageable(model, 1, "all-regions");
    }

    @RequestMapping(value = "/personalizedOffers/user/{userId}")
    public String predictRelevancesFromUserData(
            Model model,
            @PathVariable("userId") int userId) {
        cvProfile = null;
        RegisteredUser user = personService.findRegisteredUserById(userId);
        relevanceScore = new RelevanceScore(user.getFieldsRelevancy().getRelevanceScoresArray());
        return showIndexWithPersonalizedOffersPageable(model, 1, user.getRegion().getId().replaceAll("/", "-"));
    }

    @RequestMapping(value = "/personalizedOffers/region/{regionId}/page/{pageNumber}")
    public String showIndexWithPersonalizedOffersPageable(
            Model model, @PathVariable("pageNumber") int currentPage, @PathVariable("regionId") String regionId) {
        Collection<Offer> offers;

        if (regionId.equals("all-regions")) {
            offers = offerService.getOffersAccToPredictions(
                    currentPage, relevanceScore);
            if(!offerService.getOffersAccToPredictions(
                    currentPage+1, relevanceScore).isEmpty()){
                model.addAttribute("personalizationHasNextPage", true);
            }
        } else {
            offers = offerService.getOffersAccToPredictionsForRegion(
                    currentPage, regionId.replaceAll("-", "/"), relevanceScore);
            if(!offerService.getOffersAccToPredictionsForRegion(
                    currentPage+1, regionId.replaceAll("-", "/"), relevanceScore).isEmpty()){
                model.addAttribute("personalizationHasNextPage", true);
            }
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser;
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);
        int todayDay = LocalDate.now().getDayOfYear();
        model.addAttribute("user", registeredUser);
        model.addAttribute("offers", offers);
        model.addAttribute("cvProfile", cvProfile);
        model.addAttribute("relevanceScore", relevanceScore);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("regionId", regionId);
        model.addAttribute("todayDay", todayDay);
        return "index";
    }

    @RequestMapping(value = "/offerDetail/{offerId}")
    public String showOfferDetailPage(Model model, @PathVariable("offerId") long offerId) {
        Offer offer = offerService.findOfferById(offerId);
        String offerText = offer.getOfferText();
        String [] offerTextParagraphsArray = offerText.split("\n");
        List<String> offerTextParagraphs = Arrays.asList(offerTextParagraphsArray);
        String editDate;
        String insertionDate;
        int today = LocalDate.now().getDayOfYear();
        int insertion = offer.getInsertionDate().getDayOfYear();
        int edit = offer.getEditDate().getDayOfYear();

        if(today == edit){
            editDate = "Dnes";
        }else if(today == (edit+1)){
            editDate = "Včera";
        }else{
            editDate = offer.getEditDate().getDayOfMonth() + ". " + offer.getEditDate().getMonthValue() + ". " + offer.getEditDate().getYear();
        }

        if(today == insertion){
            insertionDate = "Dnes";
        }else if(today == (insertion+1)){
            insertionDate = "Včera";
        }else{
            insertionDate = offer.getInsertionDate().getDayOfMonth() + ". " + offer.getInsertionDate().getMonthValue() + ". " + offer.getInsertionDate().getYear();
        }

        List<String> workships = new ArrayList<>();
        for (Workship workship : offer.getWorkships()) {
            switch (workship.getCode()) {
                case "dpc":
                    workships.add("DPČ");
                    break;
                case "dpp":
                    workships.add("DPP");
                    break;
                case "plny":
                    workships.add("HPP");
                    break;
                case "sluzebni":
                    workships.add(workship.getName());
                    break;
                case "zkraceny":
                    workships.add("Zkrácený úvazek");
                    break;
            }
        }

        model.addAttribute("offer", offer);
        model.addAttribute("editDate", editDate);
        model.addAttribute("insertionDate", insertionDate);
        model.addAttribute("workships", workships);
        model.addAttribute("offerTextParagraphs", offerTextParagraphs);
        return "offerDetail";
    }


    private void addSearchedLocation(Model model, String searchedLocation) {
        model.addAttribute("searchedLocation", searchedLocation);
        if (!searchedLocation.equals("all")) {
            if (searchedLocation.contains("Kraj")) {
                model.addAttribute("searchedLocationName", regionService.findRegionById(searchedLocation).getName());
            } else {
                model.addAttribute("searchedLocationName", districtService.findDistrictById(searchedLocation).getName());
            }
        }
    }

    private void addSearchedField(Model model, String searchedFieldId) {
            model.addAttribute("searchedFieldId", searchedFieldId);
        if (!searchedFieldId.equals("all")) {
            model.addAttribute("searchedFieldName", fieldService.findFieldById(searchedFieldId).getName());
        }
    }
}