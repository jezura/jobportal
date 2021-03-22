package jobportal.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.codebooks.Field;
import jobportal.services.FieldService;
import jobportal.services.FirstContactService;
import jobportal.services.OfferService;
import jobportal.services.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping({"/admin/overview"})
    public String showAdminOverview() {
        return "admin/adminOverview";
    }

    @RequestMapping({"/admin/codebook"})
    public String showAdminCodebookPage() {
        return "admin/adminCodebook";
    }

    @RequestMapping({"/admin/offerDataLoad"})
    public String showAdminOfferDataLoadPage() {
        return "admin/adminOfferDataLoad";
    }

    @RequestMapping({"/admin/offerDataDelete"})
    public String showAdminOfferDataDeletePage() {
        return "admin/adminOfferDataDelete";
    }

    @RequestMapping({"/admin/allOffers"})
    public String showAdminAllOffers(Model model) {
        return this.showAdminAllOffersPageable(model, 1);
    }

    @GetMapping({"/admin/allOffers/page/{pageNumber}"})
    public String showAdminAllOffersPageable(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Offer> offerPage = this.offerService.findAllOffersPageable(currentPage, 20);
        long totalOffers = offerPage.getTotalElements();
        int totalPages = offerPage.getTotalPages();
        int lastOfferNum = currentPage * 20;
        int firstOfferNum = lastOfferNum - 20 + 1;
        if ((long)lastOfferNum > totalOffers) {
            lastOfferNum = (int)totalOffers;
        }

        Collection<Offer> offers = offerPage.getContent();
        Collection<Field> fields = this.fieldService.findAllFields();
        model.addAttribute("offers", offers);
        model.addAttribute("fields", fields);
        model.addAttribute("totalOffers", totalOffers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstOfferNum", firstOfferNum);
        model.addAttribute("lastOfferNum", lastOfferNum);
        model.addAttribute("message_notification", this.message_notification);
        this.message_notification = "";
        return "admin/adminAllOffers";
    }

    @PostMapping({"/admin/deleteOffer/{id}"})
    public String deleteOffer(@PathVariable(name = "id") Long id) {
        this.offerService.deleteOffer(this.offerService.findOfferById(id));
        this.message_notification = "Vybraná pracovní nabídka byla úspěšně smazána";
        return "redirect:/admin/allOffers";
    }

    @RequestMapping({"/admin/updateOffer/{id}"})
    public String updateOffer(@PathVariable(name = "id") Long id, @RequestParam("offerTitle") String offerTitle, @RequestParam("offerText") String offerText) {
        System.out.println("New offer title is: " + offerTitle);
        Offer offer = this.offerService.findOfferById(id);
        offer.setTitle(offerTitle);
        offer.setOfferText(offerText);
        this.offerService.saveOffer(offer);
        this.message_notification = "Vybraná pracovní nabídka byla úspěšně editována";
        return "redirect:/admin/allOffers";
    }

    @RequestMapping({"/titlesAutocomplete"})
    @ResponseBody
    public List<String> autoTitle(@RequestParam(value = "term",required = false,defaultValue = "") String term) {
        List<String> titles = this.offerService.findTitlesLikeSearchTerm(term);
        return titles;
    }

    @PostMapping({"/admin/searchOffers"})
    public String showAdminFilteredOffers(Model model, @RequestParam(name = "titleSearch",required = false) String titleSearch, @RequestParam(name = "idSearch",required = false) String idSearch, @RequestParam(name = "fieldIdSearch",required = false) String fieldIdSearch, @RequestParam(name = "page",required = false) String page) {
        int currentPage;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        } else {
            currentPage = 1;
        }

        if (titleSearch.isBlank() && idSearch.isBlank() && fieldIdSearch.equals("all")) {
            return this.showAdminAllOffersPageable(model, 1);
        } else {
            return idSearch.isBlank() ? this.showAdminFilteredOffersPageable(model, currentPage, 0, titleSearch, fieldIdSearch) : this.showAdminFilteredOffersPageable(model, currentPage, Long.valueOf(idSearch), titleSearch, fieldIdSearch);
        }
    }

    @GetMapping({"/admin/searchedOffers/page/{pageNumber}"})
    public String showAdminFilteredOffersPageable(Model model, @PathVariable("pageNumber") int currentPage, long offerId, String titleSearch, String fieldIdSearch) {
        List<Offer> offers = new ArrayList();
        if (offerId > 0) {
            Offer offer = this.offerService.findOfferById(offerId);
            model.addAttribute("searchedId", offerId);
            if (offer != null) {
                offers.add(offer);
            }
        } else {
            Page offerPage;
            if (!titleSearch.isBlank() && !fieldIdSearch.equals("all")) {
                offerPage = this.offerService.findByTitleLikeAndFieldIdPageable(titleSearch, fieldIdSearch, currentPage, 20);
                model.addAttribute("searchedTitle", titleSearch);
                model.addAttribute("searchedFieldId", fieldIdSearch);
                model.addAttribute("searchedFieldName", this.fieldService.findFieldById(fieldIdSearch).getName());
            } else if (!titleSearch.isBlank()) {
                offerPage = this.offerService.findByTitleContainingIgnoreCase(titleSearch, currentPage, 20);
                model.addAttribute("searchedTitle", titleSearch);
                model.addAttribute("searchedFieldId", "all");
            } else {
                offerPage = this.offerService.findByFieldIdPageable(fieldIdSearch, currentPage, 20);
                model.addAttribute("searchedFieldId", fieldIdSearch);
                model.addAttribute("searchedFieldName", this.fieldService.findFieldById(fieldIdSearch).getName());
            }

            long totalOffers = offerPage.getTotalElements();
            int totalPages = offerPage.getTotalPages();
            int lastOfferNum = currentPage * 20;
            int firstOfferNum = lastOfferNum - 20 + 1;
            if ((long)lastOfferNum > totalOffers) {
                lastOfferNum = (int)totalOffers;
            }

            offers = offerPage.getContent();
            model.addAttribute("firstOfferNum", firstOfferNum);
            model.addAttribute("lastOfferNum", lastOfferNum);
            model.addAttribute("totalOffers", totalOffers);
            model.addAttribute("totalPages", totalPages);
        }

        System.out.println("Offers list size: " + offers.size());
        Collection<Field> fields = this.fieldService.findAllFields();
        model.addAttribute("fields", fields);
        model.addAttribute("offers", offers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searching", true);
        model.addAttribute("message_notification", this.message_notification);
        this.message_notification = "";
        return "admin/adminAllOffers";
    }
}