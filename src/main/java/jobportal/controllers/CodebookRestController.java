package jobportal.controllers;

import jobportal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Rest Controller created to check codebook data tables, if they have complete data
 */
@RestController
public class CodebookRestController {

    @Autowired
    private RegionService regionService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private VillageService villageService;
    @Autowired
    private VillagePartService villagePartService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private PlaceTypeService placeTypeService;
    @Autowired
    private BenefitService benefitService;
    @Autowired
    private SuitabilityService suitabilityService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private WorkshipService workshipService;
    @Autowired
    private WorkshiftService workshiftService;
    @Autowired
    private EducationService educationService;


    /**
     * Method checks if all codebook tables are populated with data, if so it will return true.
     * @return - boolean (If true, all codebook tables are already populated with data..
     */
    @GetMapping(value = "/admin/checkCodebookData")
    public boolean checkIfAllCodebookDataPresent() {
        if((benefitService.getCount() > 0)
                && (districtService.getCount() > 0)
                && (educationService.getCount() > 0)
                && (fieldService.getCount() > 0)
                && (languageService.getCount() > 0)
                && (placeTypeService.getCount() > 0)
                && (professionService.getCount() > 0)
                && (regionService.getCount() > 0)
                && (skillService.getCount() > 0)
                && (suitabilityService.getCount() > 0)
                && (villagePartService.getCount() > 0)
                && (villageService.getCount() > 0)
                && (workshiftService.getCount() > 0)
                && (workshipService.getCount() > 0)
        ){
            return true;
        }else{
            return false;
        }
    }
}