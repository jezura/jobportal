package jobportal.controllers;
import jobportal.models.Offer;
import jobportal.services.FirstContactService;
import jobportal.services.OfferService;
import jobportal.services.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
        model.addAttribute("offers", offers);
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
}
