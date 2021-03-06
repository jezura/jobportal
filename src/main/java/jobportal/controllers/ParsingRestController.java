package jobportal.controllers;

import jobportal.models.offer_data_models.Employer;
import jobportal.models.offer_data_models.FirstContact;
import jobportal.models.offer_data_models.Offer;
import jobportal.models.offer_data_models.WorkPlace;
import jobportal.models.offer_data_models.codebooks.*;
import jobportal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RestController
public class ParsingRestController {
    private int codebookParsingProgress = 0;
    private int codebookParsedRecords = 0;
    private int parsedOffersCount = 1;
    private String actualParsedOfferInsertionDate = "zatím žádný";
    private String actualParsedOfferEditDate = "zatím žádný";

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
    private EmployerService employerService;
    @Autowired
    private SuitabilityService suitabilityService;
    @Autowired
    private OfferSkillService offerSkillService;
    @Autowired
    private OfferBenefitService offerBenefitService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private OfferLanguageService offerLanguageService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private WorkshipService workshipService;
    @Autowired
    private WorkshiftService workshiftService;
    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private FirstContactService firstContactService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private OfferService offerService;


    // method returns codebook parsing progress, it is number of processed codebook tables from total
    @GetMapping(value = "/admin/codebookParsingProgress")
    public int getCodebookParsingProgress() {
        return codebookParsingProgress;
    }

    // method returns actual count of already parsed and loaded codebook data records
    @GetMapping(value = "/admin/codebookParsedRecords")
    public int getCodebookParsedRecords() {
        return codebookParsedRecords;
    }

    // method returns insertion date of actually parsed job offer
    @GetMapping(value = "/admin/actualParsedOfferInsertionDate")
    public String getActualParsedOfferInsertionDate() {
        return actualParsedOfferInsertionDate;
    }

    // method returns edit date of actually parsed job offer
    @GetMapping(value = "/admin/actualParsedOfferEditDate")
    public String getActualParsedOfferEditDate() {
        return actualParsedOfferEditDate;
    }

    // method returns progress/actual count of already parsed and loaded job offers data records
    @GetMapping(value = "/admin/parsedOffersCount")
    public int getParsedOffersCount() {
        return parsedOffersCount;
    }

    // method parse all codebook data and store to the app database, if success returns true
    @GetMapping(value = "/admin/parseAllCodeBookData")
    public boolean parseAllCodeBookData() throws IOException, ParseException {
        File file = new ClassPathResource("kraje.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Region region = new Region();
            region.setId(nextObject.get("id").toString());
            region.setCode(nextObject.get("kod").toString());
            region.setKodNuts3(nextObject.get("kodNuts3").toString());
            region.setName(nextObject.get("nazev").toString());

            regionService.saveRegion(region);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("okresy.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            District district = new District();
            district.setId(nextObject.get("id").toString());
            district.setCode(nextObject.get("kod").toString());
            district.setKodLau(nextObject.get("kodLau").toString());
            district.setName(nextObject.get("nazev").toString());
            district.setRegion(regionService.findRegionById(nextObject.get("kraj").toString()));

            districtService.saveDistrict(district);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("obce.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Village village = new Village();
            village.setId(nextObject.get("id").toString());
            village.setCode(nextObject.get("kod").toString());
            village.setName(nextObject.get("nazev").toString());
            village.setDistrict(districtService.findDistrictById(nextObject.get("okres").toString()));

            villageService.saveVillage(village);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("casti-obci.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            VillagePart villagePart = new VillagePart();
            villagePart.setId(nextObject.get("id").toString());
            villagePart.setCode(nextObject.get("kod").toString());
            villagePart.setName(nextObject.get("nazev").toString());

            villagePartService.saveVillagePart(villagePart);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("dovednosti.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Skill skill = new Skill();
            skill.setId(nextObject.get("id").toString());
            skill.setCode(nextObject.get("kod").toString());
            skill.setName(nextObject.get("nazev").toString());

            skillService.saveSkill(skill);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("typy-mista-vykonu-prace.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            PlaceType placeType = new PlaceType();
            placeType.setId(nextObject.get("id").toString());
            placeType.setCode(nextObject.get("kod").toString());
            placeType.setName(nextObject.get("nazev").toString());

            placeTypeService.savePlaceType(placeType);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("vhodnosti-pro-typ-zamestnance.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Suitability suitability = new Suitability();
            suitability.setId(nextObject.get("id").toString());
            suitability.setCode(nextObject.get("kod").toString());
            suitability.setName(nextObject.get("nazev").toString());

            suitabilityService.saveSuitability(suitability);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("vyhody-volneho-mista.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Benefit benefit = new Benefit();
            benefit.setId(nextObject.get("id").toString());
            benefit.setCode(nextObject.get("kod").toString());
            benefit.setName(nextObject.get("nazev").toString());

            benefitService.saveBenefit(benefit);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("jazyky.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Language language = new Language();
            language.setId(nextObject.get("id").toString());
            language.setCode(nextObject.get("kod").toString());
            language.setName(nextObject.get("nazev").toString());

            languageService.saveLanguage(language);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("obory-cinnosti-vm.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Field field = new Field();
            field.setId(nextObject.get("id").toString());
            field.setCode(nextObject.get("kod").toString());
            field.setName(nextObject.get("nazev").toString());

            fieldService.saveField(field);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("czisco.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Profession profession = new Profession();
            profession.setId(nextObject.get("id").toString());
            profession.setCode(nextObject.get("kod").toString());
            profession.setName(nextObject.get("nazev").toString());

            // !! oboru muze byt vice polozek (a nebo klidne zadny) - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
            JSONArray fields = (JSONArray) nextObject.get("oboryCinnosti");
            try {
                Iterator<JSONObject> iteratorFields = fields.iterator();
                Set<Field> fieldSet = new HashSet<Field>();

                while (iteratorFields.hasNext()) {
                    JSONObject fieldObject = iteratorFields.next();
                    fieldSet.add(fieldService.findFieldById(fieldObject.get("id").toString()));
                }
                profession.setFields(fieldSet);
            }
            catch (java.lang.NullPointerException exception)
            {
                System.out.println("nullpointer exception");
            }

            professionService.saveProfession(profession);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("pracovnepravni-vztahy.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Workship workship = new Workship();
            workship.setId(nextObject.get("id").toString());
            workship.setCode(nextObject.get("kod").toString());
            workship.setName(nextObject.get("nazev").toString());

            workshipService.saveWorkship(workship);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("smennosti.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Workshift workshift = new Workshift();
            workshift.setId(nextObject.get("id").toString());
            workshift.setCode(nextObject.get("kod").toString());
            workshift.setName(nextObject.get("nazev").toString());

            workshiftService.saveWorkshift(workshift);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        file = new ClassPathResource("vzdelani.json").getFile();
        obj = new JSONParser().parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Education education = new Education();
            education.setId(nextObject.get("id").toString());
            education.setCode(nextObject.get("kod").toString());
            education.setName(nextObject.get("nazev").toString());

            educationService.saveEducation(education);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;
        resetCodebookCounters();

        return true;
    }

    @GetMapping(value = "/admin/parseRegions")
    public String parseRegions() throws IOException, ParseException {
        File file = new ClassPathResource("kraje.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Region region = new Region();
            region.setId(nextObject.get("id").toString());
            region.setCode(nextObject.get("kod").toString());
            region.setKodNuts3(nextObject.get("kodNuts3").toString());
            region.setName(nextObject.get("nazev").toString());

            regionService.saveRegion(region);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseDistricts")
    public String parseDistricts() throws IOException, ParseException {
        File file = new ClassPathResource("okresy.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            District district = new District();
            district.setId(nextObject.get("id").toString());
            district.setCode(nextObject.get("kod").toString());
            district.setKodLau(nextObject.get("kodLau").toString());
            district.setName(nextObject.get("nazev").toString());
            district.setRegion(regionService.findRegionById(nextObject.get("kraj").toString()));

            districtService.saveDistrict(district);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseVillages")
    public String parseVillages() throws IOException, ParseException {
        File file = new ClassPathResource("obce.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Village village = new Village();
            village.setId(nextObject.get("id").toString());
            village.setCode(nextObject.get("kod").toString());
            village.setName(nextObject.get("nazev").toString());
            village.setDistrict(districtService.findDistrictById(nextObject.get("okres").toString()));

            villageService.saveVillage(village);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseSkills")
    public String parseSkills() throws IOException, ParseException {
        File file = new ClassPathResource("dovednosti.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Skill skill = new Skill();
            skill.setId(nextObject.get("id").toString());
            skill.setCode(nextObject.get("kod").toString());
            skill.setName(nextObject.get("nazev").toString());

            skillService.saveSkill(skill);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseLanguages")
    public String parseLanguages() throws IOException, ParseException {
        File file = new ClassPathResource("jazyky.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Language language = new Language();
            language.setId(nextObject.get("id").toString());
            language.setCode(nextObject.get("kod").toString());
            language.setName(nextObject.get("nazev").toString());

            languageService.saveLanguage(language);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseFields")
    public String parseFields() throws IOException, ParseException {
        File file = new ClassPathResource("obory-cinnosti-vm.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Field field = new Field();
            field.setId(nextObject.get("id").toString());
            field.setCode(nextObject.get("kod").toString());
            field.setName(nextObject.get("nazev").toString());

            fieldService.saveField(field);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseProfessions")
    public String parseProfessions() throws IOException, ParseException {
        File file = new ClassPathResource("profese-czisco.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Profession profession = new Profession();
            profession.setId(nextObject.get("id").toString());
            profession.setCode(nextObject.get("kod").toString());
            profession.setName(nextObject.get("nazev").toString());

            // !! oboru muze byt vice polozek - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
            JSONArray fields = (JSONArray) nextObject.get("oboryCinnosti");
            Iterator<JSONObject> iteratorFields = fields.iterator();
            Set<Field> fieldSet = new HashSet<Field>();

            while (iteratorFields.hasNext()) {
                JSONObject fieldObject = iteratorFields.next();
                fieldSet.add(fieldService.findFieldById(fieldObject.get("id").toString()));
            }
            profession.setFields(fieldSet);

            professionService.saveProfession(profession);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseWorkships")
    public String parseWorkships() throws IOException, ParseException {
        File file = new ClassPathResource("pracovnepravni-vztahy.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Workship workship = new Workship();
            workship.setId(nextObject.get("id").toString());
            workship.setCode(nextObject.get("kod").toString());
            workship.setName(nextObject.get("nazev").toString());

            workshipService.saveWorkship(workship);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseWorkshifts")
    public String parseWorkshifts() throws IOException, ParseException {
        File file = new ClassPathResource("smennosti.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Workshift workshift = new Workshift();
            workshift.setId(nextObject.get("id").toString());
            workshift.setCode(nextObject.get("kod").toString());
            workshift.setName(nextObject.get("nazev").toString());

            workshiftService.saveWorkshift(workshift);
        }

        return "redirect:/admin/index";
    }

    @GetMapping(value = "/admin/parseEducations")
    public String parseEducations() throws IOException, ParseException {
        File file = new ClassPathResource("vzdelani.json").getFile();
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();
            Education education = new Education();
            education.setId(nextObject.get("id").toString());
            education.setCode(nextObject.get("kod").toString());
            education.setName(nextObject.get("nazev").toString());

            educationService.saveEducation(education);
        }

        return "redirect:/admin/index";
    }

// method parse all job offers data according to given parameters and store to the app database, if success returns true
    @GetMapping(value = "/admin/parseOffers")
    public boolean parseOffers(
            @Param("insertionDateFrom") String insertionDateFrom,
            @Param("insertionDateTo") String insertionDateTo,
            @Param("editDateFrom") String editDateFrom,
            @Param("editDateTo") String editDateTo,
            @Param("useApiSource") String useApiSource,
            @Param("skipEmptyDescription") String skipEmptyDescription)
            throws IOException, ParseException {
        Object obj = new Object();
        LocalDate localInsertionDateFrom;
        LocalDate localInsertionDateTo;
        LocalDate localEditDateFrom;
        LocalDate localEditDateTo;

        if(!insertionDateFrom.isEmpty()) {
            localInsertionDateFrom = LocalDate.parse(insertionDateFrom);
        }else{
            localInsertionDateFrom = LocalDate.parse("2020-01-01");
        }

        if(!insertionDateTo.isEmpty()) {
            localInsertionDateTo = LocalDate.parse(insertionDateTo);
        }else{
            localInsertionDateTo = LocalDate.parse("2025-01-01");
        }

        if(!editDateFrom.isEmpty()) {
            localEditDateFrom = LocalDate.parse(editDateFrom);
        }else{
            localEditDateFrom = LocalDate.parse("2020-01-01");
        }

        if(!editDateTo.isEmpty()) {
            localEditDateTo = LocalDate.parse(editDateTo);
        }else{
            localEditDateTo = LocalDate.parse("2025-01-01");
        }

        System.out.println("Use api source: " + useApiSource);
        System.out.println("Delete empty: " + skipEmptyDescription);

        // pokud je zapnuta moznost stahovani dat z internetu
        if(useApiSource.equals("switchedOn"))
        {
            URL url = new URL("https://data.mpsv.cz/od/soubory/volna-mista/volna-mista.json");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            obj = new JSONParser().parse(in);
        }else{
            File file = new ClassPathResource("offers10.json").getFile();
            obj = new JSONParser().parse(new FileReader(file));
        }

        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        System.out.println("Mam offer objekty.");
        while (iterator.hasNext()) {
            JSONObject nextObject =  iterator.next();

            String stringDateInsertion = nextObject.get("datumVlozeni").toString();
            String correctedDateInsertion = stringDateInsertion.substring(0,10);

            System.out.println("Offer date insertion " + correctedDateInsertion);

            LocalDate localDateInsertion = LocalDate.parse(correctedDateInsertion);

            String stringDateEdit = nextObject.get("datumZmeny").toString();
            String correctedDateEdit = stringDateEdit.substring(0,10);
            LocalDate localDateEdit = LocalDate.parse(correctedDateEdit);

            LocalDate today = LocalDate.now();
            LocalDate weekAgo = today.minusWeeks(1);
            LocalDate yearAgo = today.minusYears(1);

            JSONObject zverejnovat = (JSONObject) nextObject.get("zverejnovat");

            try
            {
                // pokud datumove rozsahy odpovidaji definovanym parametrum
                if((localDateInsertion.isAfter(localInsertionDateFrom.minusDays(1)))
                        && (localDateInsertion.isBefore(localInsertionDateTo.plusDays(1)))
                        && (localDateEdit.isAfter(localEditDateFrom.minusDays(1)))
                        && (localDateEdit.isBefore(localEditDateTo.plusDays(1)))) {
                    // pokud jsou vyplneny upresnujiciInformace
                    // a pokud je povoleno zverejnovat - pridej do db
                    if((nextObject.get("upresnujiciInformace") != null)
                            && zverejnovat.get("id").toString() != "ZverejnovatVpm/ne") {
                        Offer offer = new Offer();
                        offer.setId(Long.valueOf(nextObject.get("referencniCislo").toString()));
                        offer.setOfferText(nextObject.get("upresnujiciInformace").toString());
                        offer.setTitle(nextObject.get("pozadovanaProfese").toString());
                        actualParsedOfferInsertionDate = localDateInsertion.toString();
                        actualParsedOfferEditDate = localDateEdit.toString();
                        offer.setInsertionDate(localDateInsertion.plusDays(1));
                        offer.setEditDate(localDateEdit.plusDays(1));

                        try {
                            String stringExpireDate = nextObject.get("expirace").toString();
                            LocalDate localExpireDate = LocalDate.parse(stringExpireDate);
                            offer.setExpireDate(localExpireDate);
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-ExpireDate - nullpointer exception");
                        }

                        try {
                            String stringWorkStartDate = nextObject.get("terminZahajeniPracovnihoPomeru").toString();
                            LocalDate localWorkStartDate = LocalDate.parse(stringWorkStartDate);
                            offer.setWorkStartDate(localWorkStartDate);
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-WorkStartDate - nullpointer exception");
                        }

                        try {
                            String stringWorkEndDate = nextObject.get("terminUkonceniPracovnihoPomeru").toString();
                            LocalDate localWorkEndDate = LocalDate.parse(stringWorkEndDate);
                            offer.setWorkEndDate(localWorkEndDate);
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-WorkEndDate - nullpointer exception");
                        }

                        try {
                            offer.setMonthlyRateFrom(Double.valueOf(nextObject.get("mesicniMzdaOd").toString()));
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-MonthlyRateFrom - nullpointer exception");
                        }

                        try {
                            offer.setMonthlyRateTo(Double.valueOf(nextObject.get("mesicniMzdaDo").toString()));
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-MonthlyRateTo - nullpointer exception");
                        }

                        JSONObject salaryType = (JSONObject) nextObject.get("typMzdy");
                        try {
                            offer.setSalaryType(salaryType.get("id").toString());
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-SalaryType - nullpointer exception");
                        }

                        offer.setNumberOfJobs(Integer.valueOf(nextObject.get("pocetMist").toString()));

                        JSONObject profession = (JSONObject) nextObject.get("profeseCzIsco");
                        try {
                            offer.setProfession(professionService.findProfessionById(profession.get("id").toString()));
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-Profession - nullpointer exception");
                        }

                        JSONObject shift = (JSONObject) nextObject.get("smennost");
                        try {
                            offer.setWorkshift(workshiftService.findWorkshiftById(shift.get("id").toString()));
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-WorkShift - nullpointer exception");
                        }

                        JSONObject education = (JSONObject) nextObject.get("minPozadovaneVzdelani");
                        try {
                            offer.setEducation(educationService.findEducationById(education.get("id").toString()));
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-education - nullpointer exception");
                        }

                        JSONObject employer = (JSONObject) nextObject.get("zamestnavatel");
                        if(employerService.findEmployerByIco(Integer.valueOf(employer.get("ico").toString())) != null)
                        {
                            offer.setEmployer(employerService.findEmployerByIco(Integer.valueOf(employer.get("ico").toString())));
                        }
                        else {
                            Employer emp = new Employer();
                            emp.setIco( Integer.valueOf(employer.get("ico").toString()));
                            emp.setName(employer.get("nazev").toString());
                            employerService.saveEmployer(emp);
                            offer.setEmployer(emp);
                        }

                        JSONObject fc = (JSONObject) nextObject.get("prvniKontaktSeZamestnavatelem");
                        FirstContact firstContact = new FirstContact();
                        try {
                            if (fc.get("mistoKontaktu") != null) {
                                firstContact.setContactPlace(fc.get("mistoKontaktu").toString());
                            }
                            if (fc.get("adresaKontaktu") != null) {
                                firstContact.setContactAddress(fc.get("adresaKontaktu").toString());
                            }
                            if (fc.get("kontaktniTelefon") != null) {
                                firstContact.setContactPhone(fc.get("kontaktniTelefon").toString());
                            }
                            if (fc.get("kontaktniEmail") != null) {
                                firstContact.setContactMail(fc.get("kontaktniEmail").toString());
                            }
                            if (fc.get("jmenoKontaktniOsoby") != null) {
                                firstContact.setContactPersonName(fc.get("jmenoKontaktniOsoby").toString());
                            }
                            if (fc.get("prijmeniKontaktniOsoby") != null) {
                                firstContact.setContactPersonSurname(fc.get("prijmeniKontaktniOsoby").toString());
                            }
                            if (fc.get("titulPredJmenem") != null) {
                                firstContact.setTitleBefore(fc.get("titulPredJmenem").toString());
                            }
                            if (fc.get("titulZaJmenem") != null) {
                                firstContact.setTitleAfter(fc.get("titulZaJmenem").toString());
                            }
                            if (fc.get("poziceVeSpolecnosti") != null) {
                                firstContact.setCompanyPosition(fc.get("poziceVeSpolecnosti").toString());
                            }

                            firstContactService.saveFirstContact(firstContact);
                            offer.setFirstContact(firstContact);
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-firstContact - nullpointer exception");
                        }

                        // !! dovednosti muze byt vice polozek - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
                        JSONArray skills = (JSONArray) nextObject.get("pozadovanaDovednost");
                        try {
                            Iterator<JSONObject> iteratorSkills = skills.iterator();

                            while (iteratorSkills.hasNext()) {
                                JSONObject skillObject = iteratorSkills.next();
                                JSONObject dovednostObject = (JSONObject) skillObject.get("dovednost");
                                OfferSkill offerSkill = new OfferSkill();
                                Skill skill = skillService.findSkillById(dovednostObject.get("id").toString());
                                offerSkill.setSkill(skill);
                                offerSkill.setOffer(offerService.findOfferById(Long.valueOf(nextObject.get("referencniCislo").toString())));
                                if(skillObject.get("popis") != null) {
                                    offerSkill.setDescription(skillObject.get("popis").toString());
                                }
                                offer.addOfferSkill(offerSkill);
                                offerSkill.setOffer(offer);
                                skill.addOfferSkill(offerSkill);
                                offerSkill.setSkill(skill);
                            }
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-skill - nullpointer exception");
                        }

                        // !! benefitu muze byt nula nebo vice polozek - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
                        JSONArray benefits = (JSONArray) nextObject.get("vyhodyVolnehoMista");
                        try {
                            Iterator<JSONObject> iteratorBenefits = benefits.iterator();

                            while (iteratorBenefits.hasNext()) {
                                JSONObject benefitObject = iteratorBenefits.next();
                                JSONObject vyhodaObject = (JSONObject) benefitObject.get("vyhoda");
                                OfferBenefit offerBenefit = new OfferBenefit();
                                Benefit benefit = benefitService.findBenefitById(vyhodaObject.get("id").toString());
                                offerBenefit.setBenefit(benefit);
                                offerBenefit.setOffer(offerService.findOfferById(Long.valueOf(nextObject.get("referencniCislo").toString())));
                                if(benefitObject.get("popis") != null) {
                                    offerBenefit.setDescription(benefitObject.get("popis").toString());
                                }
                                offer.addOfferBenefit(offerBenefit);
                                offerBenefit.setOffer(offer);
                                benefit.addOfferBenefit(offerBenefit);
                                offerBenefit.setBenefit(benefit);
                            }
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-benefit - nullpointer exception");
                        }

                        // !! jazyku muze byt vice polozek - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
                        JSONArray languages = (JSONArray) nextObject.get("pozadovanaJazykovaZnalost");
                        try {
                            Iterator<JSONObject> iteratorLanguages = languages.iterator();

                            while (iteratorLanguages.hasNext()) {
                                JSONObject languageObject = iteratorLanguages.next();
                                JSONObject jazykObject = (JSONObject) languageObject.get("jazyk");
                                OfferLanguage offerLanguage = new OfferLanguage();
                                Language language = languageService.findLanguageById(jazykObject.get("id").toString());
                                offerLanguage.setLanguage(language);
                                offerLanguage.setOffer(offerService.findOfferById(Long.valueOf(nextObject.get("referencniCislo").toString())));

                                JSONObject urovenObject = (JSONObject) languageObject.get("urovenZnalosti");
                                switch (urovenObject.get("id").toString()) {
                                    case "UrovenZnalostiJazyka/akt":
                                        offerLanguage.setLevel("Aktivní");
                                        break;
                                    case "UrovenZnalostiJazyka/pas":
                                        offerLanguage.setLevel("Pasivní");
                                        break;
                                    case "UrovenZnalostiJazyka/neurceno":
                                        offerLanguage.setLevel("Neurčeno");
                                        break;
                                }

                                if(languageObject.get("popis") != null) {
                                    offerLanguage.setDescription(languageObject.get("popis").toString());
                                }
                                offer.addOfferLanguage(offerLanguage);
                                offerLanguage.setOffer(offer);
                                language.addOfferLanguage(offerLanguage);
                                offerLanguage.setLanguage(language);
                            }
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-skill - nullpointer exception");
                        }
                        
                        // !! pracovnich vztahu je vice polozek - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
                        JSONArray ships = (JSONArray) nextObject.get("pracovnePravniVztahy");
                        try {
                            Iterator<JSONObject> iteratorWorkShips = ships.iterator();
                            Set<Workship> workshipSet = new HashSet<Workship>();

                            while (iteratorWorkShips.hasNext()) {
                                JSONObject workShipObject = iteratorWorkShips.next();
                                workshipSet.add(workshipService.findWorkshipById(workShipObject.get("id").toString()));
                            }
                            offer.setWorkships(workshipSet);
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-workship - nullpointer exception");
                        }

                        // !! vhodnosti muze byt nula i vice polozek - ukladaji se jako M:N do setu (id vs id) / pomocna tabulka
                        JSONArray suitabilites = (JSONArray) nextObject.get("vhodnostiPracovnihoMista");
                        try {
                            Iterator<JSONObject> iteratorSuitabilites = suitabilites.iterator();
                            Set<Suitability> suitabilitySet = new HashSet<Suitability>();

                            while (iteratorSuitabilites.hasNext()) {
                                JSONObject suitabilityObject = iteratorSuitabilites.next();
                                suitabilitySet.add(suitabilityService.findSuitabilityById(suitabilityObject.get("id").toString()));
                            }
                            offer.setSuitabilities(suitabilitySet);
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-suitability - nullpointer exception");
                        }

                        JSONObject place = (JSONObject) nextObject.get("mistoVykonuPrace");
                        try {
                            JSONObject placeType = (JSONObject) place.get("typMistaVykonuPrace");
                            offer.setPlaceType(placeTypeService.findPlaceTypeById(placeType.get("id").toString()));
                        }
                        catch (java.lang.NullPointerException exception)
                        {
                            System.out.println("Offer-placeType - nullpointer exception");
                        }

                        // Work place - misto pracoviste podle typu pracoviste
                        if((offer.getPlaceType().getCode().equals("obec")) || (offer.getPlaceType().getCode().equals("adrvolna")))
                        {
                            WorkPlace workPlace = new WorkPlace();

                            JSONObject village = (JSONObject) place.get("obec");
                            try {
                                workPlace.setVillage(villageService.findVillageById(village.get("id").toString()));
                            }
                            catch (java.lang.NullPointerException exception)
                            {
                                System.out.println("Workplace-village - nullpointer exception");
                            }

                            JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                            Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                            JSONObject pracovisteObject = iteratorPracoviste.next();

                            if(pracovisteObject.get("email") != null)
                                workPlace.setEmail(pracovisteObject.get("email").toString());
                            if(pracovisteObject.get("nazev") != null)
                                workPlace.setName(pracovisteObject.get("nazev").toString());
                            if(pracovisteObject.get("telefon") != null)
                                workPlace.setPhone(pracovisteObject.get("telefon").toString());
                            workPlaceService.saveWorkPlace(workPlace);

                            offer.setWorkPlace(workPlace);
                        }
                        else
                            if(offer.getPlaceType().getCode().equals("okres")) {
                                WorkPlace workPlace = new WorkPlace();

                                JSONArray districts = (JSONArray) place.get("okresy");
                                try {
                                    Iterator<JSONObject> iteratorDistricts = districts.iterator();
                                    Set<District> districtSet = new HashSet<District>();

                                    while (iteratorDistricts.hasNext()) {
                                        JSONObject districtObject = iteratorDistricts.next();
                                        districtSet.add(districtService.findDistrictById(districtObject.get("id").toString()));
                                    }
                                    workPlace.setDistricts(districtSet);
                                }
                                catch (java.lang.NullPointerException exception)
                                {
                                    System.out.println("Workplace-districts - nullpointer exception");
                                }

                                JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                                Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                                JSONObject pracovisteObject = iteratorPracoviste.next();

                                if(pracovisteObject.get("email") != null)
                                    workPlace.setEmail(pracovisteObject.get("email").toString());
                                if(pracovisteObject.get("nazev") != null)
                                    workPlace.setName(pracovisteObject.get("nazev").toString());
                                if(pracovisteObject.get("telefon") != null)
                                    workPlace.setPhone(pracovisteObject.get("telefon").toString());
                                workPlaceService.saveWorkPlace(workPlace);

                                offer.setWorkPlace(workPlace);
                            }
                            else
                                if(offer.getPlaceType().getCode().equals("adrprov")) {
                                    WorkPlace workPlace = new WorkPlace();

                                    JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                                    Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                                    JSONObject pracovisteObject = iteratorPracoviste.next();

                                    if(pracovisteObject.get("email") != null)
                                        workPlace.setEmail(pracovisteObject.get("email").toString());
                                    if(pracovisteObject.get("nazev") != null)
                                        workPlace.setName(pracovisteObject.get("nazev").toString());
                                    if(pracovisteObject.get("telefon") != null)
                                        workPlace.setPhone(pracovisteObject.get("telefon").toString());

                                    JSONObject addressObject = (JSONObject) pracovisteObject.get("adresa");
                                    if(addressObject.get("cisloDomovni") != null)
                                       workPlace.setHouseNumber(addressObject.get("cisloDomovni").toString());
                                    if(addressObject.get("cisloOrientacni") != null)
                                        workPlace.setOrientationNumber(addressObject.get("cisloOrientacni").toString());
                                    if(addressObject.get("psc") != null)
                                        workPlace.setZipCode(addressObject.get("psc").toString());

                                    JSONObject streetObject = (JSONObject) addressObject.get("ulice");
                                    if(streetObject != null)
                                        workPlace.setStreet(streetObject.get("nazev").toString());

                                    JSONObject villageObject = (JSONObject) addressObject.get("obec");
                                    try {
                                        workPlace.setVillage(villageService.findVillageById(villageObject.get("id").toString()));
                                    }
                                    catch (java.lang.NullPointerException exception)
                                    {
                                         System.out.println("Workplace-address-village - nullpointer exception");
                                    }

                                    JSONObject villagePartObject = (JSONObject) addressObject.get("castObce");
                                    try {
                                        workPlace.setVillagePart(villagePartService.findVillagePartById(villagePartObject.get("id").toString()));
                                    }
                                    catch (java.lang.NullPointerException exception)
                                    {
                                        System.out.println("Workplace-address-villagePart - nullpointer exception");
                                    }

                                    workPlaceService.saveWorkPlace(workPlace);

                                    offer.setWorkPlace(workPlace);
                                }
                                else
                                    if(offer.getPlaceType().getCode().equals("celaCR")) {
                                        WorkPlace workPlace = new WorkPlace();

                                        JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                                        Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                                        JSONObject pracovisteObject = iteratorPracoviste.next();

                                        if(pracovisteObject.get("email") != null)
                                            workPlace.setEmail(pracovisteObject.get("email").toString());
                                        if(pracovisteObject.get("nazev") != null)
                                            workPlace.setName(pracovisteObject.get("nazev").toString());
                                        if(pracovisteObject.get("telefon") != null)
                                            workPlace.setPhone(pracovisteObject.get("telefon").toString());
                                        workPlaceService.saveWorkPlace(workPlace);

                                        offer.setWorkPlace(workPlace);
                                    }
                                    offerService.saveOffer(offer);
                                    parsedOffersCount++;

                    }
                }
            }
            catch (java.lang.NullPointerException exception)
            {
               System.out.println("Offer - nullpointer exception");
            }
        }
        parsedOffersCount = 1;
        actualParsedOfferInsertionDate = "zatím žádný";
        actualParsedOfferEditDate = "zatím žádný";
        return true;
    }

    private void resetCodebookCounters() {
        codebookParsedRecords = 0;
        codebookParsingProgress = 0;
    }
}