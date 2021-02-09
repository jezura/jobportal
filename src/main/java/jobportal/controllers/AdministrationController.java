package jobportal.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdministrationController {

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
}
