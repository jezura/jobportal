package jobportal.controllers;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.codebooks.Field;
import jobportal.models.offer_data_models.codebooks.Region;
import jobportal.services.FieldService;
import jobportal.services.FirstContactService;
import jobportal.services.OfferService;
import jobportal.services.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class AdministrationController {
    private String message_notification = "";
    private static final int pageSize = 20;

    @Autowired
    private OfferService offerService;
    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private FirstContactService firstContactService;
    @Autowired
    private FieldService fieldService;

    @RequestMapping(value = "/admin/overview")
    public String showAdminOverview() {
        return "adminOverview";
    }

    @RequestMapping(value = "/admin/codebook")
    public String showAdminCodebookPage() {
        return "adminCodebook";
    }

    @RequestMapping(value = "/admin/offerDataLoad")
    public String showAdminOfferDataLoadPage() {
        return "adminOfferDataLoad";
    }

    @RequestMapping(value = "/admin/offerDataDelete")
    public String showAdminOfferDataDeletePage() {
        return "adminOfferDataDelete";
    }

    @RequestMapping(value = "/admin/allOffers")
    public String showAdminAllOffers(Model model) {
        return showAdminAllOffersPageable(model, 1);
    }

    @GetMapping(value = "/admin/allOffers/page/{pageNumber}")
    public String showAdminAllOffersPageable(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Offer> offerPage = offerService.listAllOffers(currentPage, pageSize);
        long totalOffers = offerPage.getTotalElements();
        int totalPages = offerPage.getTotalPages();
        int lastOfferNum = currentPage * pageSize;
        int firstOfferNum = lastOfferNum - pageSize + 1;
        if(lastOfferNum > totalOffers) {
            lastOfferNum = (int) totalOffers;
        }
        Collection<Offer> offers = offerPage.getContent();
        Collection<Field> fields = fieldService.findAllFields();

        model.addAttribute("offers", offers);
        model.addAttribute("fields", fields);
        model.addAttribute("totalOffers", totalOffers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstOfferNum", firstOfferNum);
        model.addAttribute("lastOfferNum", lastOfferNum);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "adminAllOffers";
    }

    @PostMapping(value = "/admin/deleteOffer/{id}")
    public String deleteOffer(@PathVariable(name = "id") Long id) {
        offerService.deleteOffer(offerService.findOfferById(id));
        message_notification = "Vybraná pracovní nabídka byla úspěšně smazána";
        return "redirect:/admin/allOffers";
    }

    @RequestMapping(value = "/admin/updateOffer/{id}")
    public String updateOffer(@PathVariable(name = "id") Long id,
                              @RequestParam("offerTitle") String offerTitle,
                              @RequestParam("offerText") String offerText) {
        System.out.println("New offer title is: " + offerTitle);
        Offer offer = offerService.findOfferById(id);
        offer.setTitle(offerTitle);
        offer.setOfferText(offerText);
        offerService.saveOffer(offer);
        message_notification = "Vybraná pracovní nabídka byla úspěšně editována";
        return "redirect:/admin/allOffers";
    }

    @RequestMapping(value = "/titlesAutocomplete")
    @ResponseBody
    public List<String> autoTitle(@RequestParam(value = "term", required = false, defaultValue = "")String term){
        List<String> titles = offerService.findTitlesLikeSearchTerm(term);
        return titles;
    }

    @PostMapping(value = "/admin/searchOffers")
    public String showFilteredOffers(Model model, @RequestParam(name = "titleSearch", required = false) String titleSearch,
                                     @RequestParam(name = "idSearch", required = false) String idSearch,
                                     @RequestParam(name = "fieldIdSearch", required = false) String fieldIdSearch){
        System.out.println("Field ID: " + fieldIdSearch);

        if((titleSearch.isBlank()) && (idSearch.isBlank()) && (fieldIdSearch.equals("all"))) {
            return showAdminAllOffersPageable(model, 1);
        }else if(idSearch.isBlank()){
            return showAdminFilteredOffersPageable(model, 1, 0, titleSearch, fieldIdSearch);
        }
        return showAdminFilteredOffersPageable(model, 1, Long.valueOf(idSearch), titleSearch, fieldIdSearch);
    }

    @GetMapping(value = "/admin/searchedOffers/page/{pageNumber}")
    public String showAdminFilteredOffersPageable(Model model, @PathVariable("pageNumber") int currentPage, long offerId, String titleSearch, String fieldIdSearch) {
        List<Offer> offers = new ArrayList<>();
        if(offerId > 0){
            Offer offer = offerService.findOfferById(offerId);
            model.addAttribute("searchedId", offerId);
            if(offer != null){
                offers.add(offer);
            }
        }else{
            Page<Offer> offerPage;
            if((!titleSearch.isBlank()) && (!fieldIdSearch.equals("all"))){
                offerPage = offerService.findByTitleLikeAndFieldIdPageable(titleSearch, fieldIdSearch, currentPage, pageSize);
                model.addAttribute("searchedTitle", titleSearch);
                model.addAttribute("searchedFieldId", fieldIdSearch);
                model.addAttribute("searchedFieldName", fieldService.findFieldById(fieldIdSearch).getName());
            }else if (!titleSearch.isBlank()) {
                offerPage = offerService.findByTitleContainingIgnoreCase(titleSearch, currentPage, pageSize);
                model.addAttribute("searchedTitle", titleSearch);
            }else{
                offerPage = offerService.findByFieldIdPageable(fieldIdSearch, currentPage, pageSize);
                model.addAttribute("searchedFieldId", fieldIdSearch);
                model.addAttribute("searchedFieldName", fieldService.findFieldById(fieldIdSearch).getName());
            }
            long totalOffers = offerPage.getTotalElements();
            int totalPages = offerPage.getTotalPages();
            int lastOfferNum = currentPage * pageSize;
            int firstOfferNum = lastOfferNum - pageSize + 1;
            if(lastOfferNum > totalOffers) {
                lastOfferNum = (int) totalOffers;
            }
            offers = offerPage.getContent();

            model.addAttribute("firstOfferNum", firstOfferNum);
            model.addAttribute("lastOfferNum", lastOfferNum);
            model.addAttribute("totalOffers", totalOffers);
            model.addAttribute("totalPages", totalPages);
        }

        System.out.println("Offers list size: " + offers.size());

        Collection<Field> fields = fieldService.findAllFields();

        model.addAttribute("fields", fields);
        model.addAttribute("offers", offers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "adminAllOffers";
    }
}
