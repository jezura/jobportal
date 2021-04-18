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

/**
 * REST Controller responsible for whole parsing processes for offers data as well as all codebook data.
 * ParsingRestController is used during new data loading process - it transforms/parse all common source MPSV data to local
 * objects which are then saved/persisted to DB.>
 * ParsingRestController is one of the most important controllers in this app - it realizes connection to open data
 * of the Ministry of Labor and Social Affairs CZ.
 */
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

    /**
     * Method returns codebook parsing progress, it is number of already processed codebook tables from total
     *
     * @return int - number of already processed codebook tables from total
     */
    @GetMapping(value = "/admin/codebookParsingProgress")
    public int getCodebookParsingProgress() {
        return codebookParsingProgress;
    }

    /**
     * Method returns actual count of already parsed and loaded codebook data records
     *
     * @return int - count of already parsed and loaded codebook data records
     */
    @GetMapping(value = "/admin/codebookParsedRecords")
    public int getCodebookParsedRecords() {
        return codebookParsedRecords;
    }

    /**
     * Method returns insertion date of actually parsed job offer
     *
     * @return String - insertion date
     */
    @GetMapping(value = "/admin/actualParsedOfferInsertionDate")
    public String getActualParsedOfferInsertionDate() {
        return actualParsedOfferInsertionDate;
    }

    /**
     * Method returns edit date of actually parsed job offer
     *
     * @return String - edit date
     */
    @GetMapping(value = "/admin/actualParsedOfferEditDate")
    public String getActualParsedOfferEditDate() {
        return actualParsedOfferEditDate;
    }

    /**
     * Method returns progress/actual count of already parsed and loaded job offers data records
     *
     * @return int - count of already parsed and loaded job offers data records
     */
    @GetMapping(value = "/admin/parsedOffersCount")
    public int getParsedOffersCount() {
        return parsedOffersCount;
    }

    /**
     * Method parse all codebook data and store to the app database, if success returns true.
     *
     * @return boolean (If true, parsing was success)
     * @throws IOException    - data source file/API exception
     * @throws ParseException - parsing exception
     */
    @GetMapping(value = "/admin/parseAllCodeBookData")
    public boolean parseAllCodeBookData() throws IOException, ParseException {
        // kraje (regions) parsing
        URL url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/kraje.json");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        Object obj = new JSONParser().parse(in);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Region region = new Region();
            region.setId(nextObject.get("id").toString());
            region.setCode(nextObject.get("kod").toString());
            region.setKodNuts3(nextObject.get("kodNuts3").toString());
            region.setName(nextObject.get("nazev").toString());

            regionService.saveRegion(region);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // okresy (districts) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/okresy.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
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

        // obce (villages) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/obce.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Village village = new Village();
            village.setId(nextObject.get("id").toString());
            village.setCode(nextObject.get("kod").toString());
            village.setName(nextObject.get("nazev").toString());
            village.setDistrict(districtService.findDistrictById(nextObject.get("okres").toString()));

            villageService.saveVillage(village);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // casti obci (village parts) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/casti-obci.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            VillagePart villagePart = new VillagePart();
            villagePart.setId(nextObject.get("id").toString());
            villagePart.setCode(nextObject.get("kod").toString());
            villagePart.setName(nextObject.get("nazev").toString());

            villagePartService.saveVillagePart(villagePart);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // kraje (regions) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/dovednosti.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Skill skill = new Skill();
            skill.setId(nextObject.get("id").toString());
            skill.setCode(nextObject.get("kod").toString());
            skill.setName(nextObject.get("nazev").toString());

            skillService.saveSkill(skill);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // typy mista vykonu prace (place types) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/typy-mista-vykonu-prace.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            PlaceType placeType = new PlaceType();
            placeType.setId(nextObject.get("id").toString());
            placeType.setCode(nextObject.get("kod").toString());
            placeType.setName(nextObject.get("nazev").toString());

            placeTypeService.savePlaceType(placeType);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // vhodnosti pro typ zamestnance (suitabilities) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/vhodnosti-pro-typ-zamestnance.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Suitability suitability = new Suitability();
            suitability.setId(nextObject.get("id").toString());
            suitability.setCode(nextObject.get("kod").toString());
            suitability.setName(nextObject.get("nazev").toString());

            suitabilityService.saveSuitability(suitability);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // vyhody volnych mist (benefits) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/vyhody-volneho-mista.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Benefit benefit = new Benefit();
            benefit.setId(nextObject.get("id").toString());
            benefit.setCode(nextObject.get("kod").toString());
            benefit.setName(nextObject.get("nazev").toString());

            benefitService.saveBenefit(benefit);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // jazyky (languages) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/jazyky.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Language language = new Language();
            language.setId(nextObject.get("id").toString());
            language.setCode(nextObject.get("kod").toString());
            language.setName(nextObject.get("nazev").toString());

            languageService.saveLanguage(language);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // obory cinnosti (fields) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/obory-cinnosti-vm.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Field field = new Field();
            field.setId(nextObject.get("id").toString());
            field.setCode(nextObject.get("kod").toString());
            field.setName(nextObject.get("nazev").toString());

            fieldService.saveField(field);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // profese (professions) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/cz-isco.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
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
            } catch (java.lang.NullPointerException exception) {
                System.out.println("nullpointer exception");
            }

            professionService.saveProfession(profession);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // pracovnepravni vztahy (work ships) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/pracovnepravni-vztahy.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Workship workship = new Workship();
            workship.setId(nextObject.get("id").toString());
            workship.setCode(nextObject.get("kod").toString());
            workship.setName(nextObject.get("nazev").toString());

            workshipService.saveWorkship(workship);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // smennosti (work shifts) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/smennosti.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
            Workshift workshift = new Workshift();
            workshift.setId(nextObject.get("id").toString());
            workshift.setCode(nextObject.get("kod").toString());
            workshift.setName(nextObject.get("nazev").toString());

            workshiftService.saveWorkshift(workshift);
            codebookParsedRecords++;
        }
        codebookParsingProgress++;

        // vzdelani (educations) parsing
        url = new URL("https://data.mpsv.cz/od/soubory/ciselniky/vzdelani.json");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);
        jsonObject = (JSONObject) obj;
        polozky = (JSONArray) jsonObject.get("polozky");

        iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();
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


    /**
     * Method parse all job offers data according to given parameters and store to the app database,
     * if success returns true.
     *
     * @return boolean (if success, returns true)
     * @throws IOException    - data source file/API exception
     * @throws ParseException - parsing exception
     */
    @GetMapping(value = "/admin/parseOffers")
    public boolean parseOffers(
            @Param("insertionDateFrom") String insertionDateFrom,
            @Param("insertionDateTo") String insertionDateTo,
            @Param("editDateFrom") String editDateFrom,
            @Param("editDateTo") String editDateTo,
            @Param("useApiSource") String useApiSource,
            @Param("skipEmptyDescription") String skipEmptyDescription)
            throws IOException, ParseException {
        Object obj;
        LocalDate localInsertionDateFrom;
        LocalDate localInsertionDateTo;
        LocalDate localEditDateFrom;
        LocalDate localEditDateTo;

        if (!insertionDateFrom.isEmpty()) {
            localInsertionDateFrom = LocalDate.parse(insertionDateFrom);
        } else {
            localInsertionDateFrom = LocalDate.parse("2020-01-01");
        }

        if (!insertionDateTo.isEmpty()) {
            localInsertionDateTo = LocalDate.parse(insertionDateTo);
        } else {
            localInsertionDateTo = LocalDate.parse("2025-01-01");
        }

        if (!editDateFrom.isEmpty()) {
            localEditDateFrom = LocalDate.parse(editDateFrom);
        } else {
            localEditDateFrom = LocalDate.parse("2020-01-01");
        }

        if (!editDateTo.isEmpty()) {
            localEditDateTo = LocalDate.parse(editDateTo);
        } else {
            localEditDateTo = LocalDate.parse("2025-01-01");
        }

        // --- DATA SOURCE ---

        // -- STANDARDNI VERZE --
        if (useApiSource.equals("switchedOn")) {
            // oficialni datovy zdroj MPSV CR s velkym souborem (300 MB)
            URL url = new URL("https://data.mpsv.cz/od/soubory/volna-mista/volna-mista.json");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            obj = new JSONParser().parse(in);
        } else {
            // verze s lokalnim stazenym JSON souborem - s daty pouze pro dny 2021_03_22-23
            File file = new ClassPathResource("offers_json_data/volna-mista-2021_03_22-23.json").getFile();
            obj = new JSONParser().parse(new FileReader(file));
        }

        // -- VERZE PRO HEROKU DEPLOYMENT --
        /*// datovy zdroj s mensim souborem s daty pouze pro dny 2021_03_22-23 - urceny pro verzi deployed na Heroku (kvuli HW pozadavkum - RAM a DB)
        URL url = new URL("https://raw.githubusercontent.com/jezura/jobportal/master/src/main/resources/offers_json_data/volna-mista-2021_03_22-23.json");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        obj = new JSONParser().parse(in);*/


        JSONObject jsonObject = (JSONObject) obj;
        JSONArray polozky = (JSONArray) jsonObject.get("polozky");

        Iterator<JSONObject> iterator = polozky.iterator();
        while (iterator.hasNext()) {
            JSONObject nextObject = iterator.next();

            String stringDateInsertion = nextObject.get("datumVlozeni").toString();
            String correctedDateInsertion = stringDateInsertion.substring(0, 10);

            System.out.println("Offer date insertion " + correctedDateInsertion);

            LocalDate localDateInsertion = LocalDate.parse(correctedDateInsertion);

            String stringDateEdit = nextObject.get("datumZmeny").toString();
            String correctedDateEdit = stringDateEdit.substring(0, 10);
            LocalDate localDateEdit = LocalDate.parse(correctedDateEdit);

            JSONObject zverejnovat = (JSONObject) nextObject.get("zverejnovat");

            try {
                // pokud datumove rozsahy odpovidaji definovanym parametrum
                if ((localDateInsertion.isAfter(localInsertionDateFrom.minusDays(1)))
                        && (localDateInsertion.isBefore(localInsertionDateTo.plusDays(1)))
                        && (localDateEdit.isAfter(localEditDateFrom.minusDays(1)))
                        && (localDateEdit.isBefore(localEditDateTo.plusDays(1)))) {
                    // pokud jsou vyplneny upresnujiciInformace
                    // a pokud je povoleno zverejnovat
                    if ((nextObject.get("upresnujiciInformace") != null)
                            && zverejnovat.get("id").toString() != "ZverejnovatVpm/ne") {
                        // z parsovanych informaci postupne vytvarej novou instanci pracovni nabidky
                        Offer offer = new Offer();
                        offer.setId(Long.valueOf(nextObject.get("referencniCislo").toString()));
                        offer.setOfferText(nextObject.get("upresnujiciInformace").toString());
                        offer.setTitle(nextObject.get("pozadovanaProfese").toString());
                        actualParsedOfferInsertionDate = localDateInsertion.toString();
                        actualParsedOfferEditDate = localDateEdit.toString();
                        offer.setInsertionDate(localDateInsertion.plusDays(1));
                        offer.setEditDate(localDateEdit.plusDays(1));

                        // nastaveni data expirace
                        try {
                            String stringExpireDate = nextObject.get("expirace").toString();
                            LocalDate localExpireDate = LocalDate.parse(stringExpireDate);
                            offer.setExpireDate(localExpireDate);
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("ExpireDate", offer.getId());
                        }

                        // nastaveni terminu zahajeni prac. pomeru
                        try {
                            String stringWorkStartDate = nextObject.get("terminZahajeniPracovnihoPomeru").toString();
                            LocalDate localWorkStartDate = LocalDate.parse(stringWorkStartDate);
                            offer.setWorkStartDate(localWorkStartDate);
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("WorkStartDate", offer.getId());
                        }

                        // nastaveni terminu ukonceni prac. pomeru
                        try {
                            String stringWorkEndDate = nextObject.get("terminUkonceniPracovnihoPomeru").toString();
                            LocalDate localWorkEndDate = LocalDate.parse(stringWorkEndDate);
                            offer.setWorkEndDate(localWorkEndDate);
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("WorkEndDate", offer.getId());
                        }

                        // nastaveni dolni hranice mesicni mzdy
                        try {
                            offer.setMonthlyRateFrom(Double.valueOf(nextObject.get("mesicniMzdaOd").toString()));
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("MonthlyRateFrom", offer.getId());
                        }

                        // nastaveni horni hranice mesicni mzdy
                        try {
                            offer.setMonthlyRateTo(Double.valueOf(nextObject.get("mesicniMzdaDo").toString()));
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("MonthlyRateTo", offer.getId());
                        }

                        // nastaveni typu mzdy
                        JSONObject salaryType = (JSONObject) nextObject.get("typMzdy");
                        try {
                            offer.setSalaryType(salaryType.get("id").toString());
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("SalaryType", offer.getId());
                        }

                        // nastaveni poctu volnych mist
                        offer.setNumberOfJobs(Integer.valueOf(nextObject.get("pocetMist").toString()));

                        // nastaveni odpovidajici profese
                        JSONObject profession = (JSONObject) nextObject.get("profeseCzIsco");
                        try {
                            offer.setProfession(professionService.findProfessionById(profession.get("id").toString()));
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("Profession", offer.getId());
                        }

                        // nastaveni odpovidajici smennosti
                        JSONObject shift = (JSONObject) nextObject.get("smennost");
                        try {
                            offer.setWorkshift(workshiftService.findWorkshiftById(shift.get("id").toString()));
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("WorkShift", offer.getId());
                        }

                        // nastaveni min. pozadovaneho vzdelani
                        JSONObject education = (JSONObject) nextObject.get("minPozadovaneVzdelani");
                        try {
                            offer.setEducation(educationService.findEducationById(education.get("id").toString()));
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("Education", offer.getId());
                        }

                        // nastaveni zamestanavatele
                        JSONObject employer = (JSONObject) nextObject.get("zamestnavatel");
                        if (employerService.findEmployerByIco(Integer.valueOf(employer.get("ico").toString())) != null) {
                            offer.setEmployer(employerService.findEmployerByIco(Integer.valueOf(employer.get("ico").toString())));
                        } else {
                            Employer emp = new Employer();
                            emp.setIco(Integer.valueOf(employer.get("ico").toString()));
                            emp.setName(employer.get("nazev").toString());
                            employerService.saveEmployer(emp);
                            offer.setEmployer(emp);
                        }

                        // nastaveni prvniho kontaktu
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
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("FirstContact", offer.getId());
                        }

                        // nastaveni odpovidajicich dovednosti
                        // !! dovednosti muze byt vice polozek - ukladaji se jako M:N >> pomocna tabulka
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
                                if (skillObject.get("popis") != null) {
                                    offerSkill.setDescription(skillObject.get("popis").toString());
                                }
                                offer.addOfferSkill(offerSkill);
                                offerSkill.setOffer(offer);
                                skill.addOfferSkill(offerSkill);
                                offerSkill.setSkill(skill);
                            }
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("Skill", offer.getId());
                        }

                        // nastaveni odpovidajicich benefitu
                        // !! benefitu muze byt vice polozek - ukladaji se jako M:N >> pomocna tabulka
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
                                if (benefitObject.get("popis") != null) {
                                    offerBenefit.setDescription(benefitObject.get("popis").toString());
                                }
                                offer.addOfferBenefit(offerBenefit);
                                offerBenefit.setOffer(offer);
                                benefit.addOfferBenefit(offerBenefit);
                                offerBenefit.setBenefit(benefit);
                            }
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("Benefit", offer.getId());
                        }

                        // nastaveni pozadovanych jazyku
                        // !! jazyku muze byt vice polozek - ukladaji se jako M:N >> pomocna tabulka
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

                                if (languageObject.get("popis") != null) {
                                    offerLanguage.setDescription(languageObject.get("popis").toString());
                                }
                                offer.addOfferLanguage(offerLanguage);
                                offerLanguage.setOffer(offer);
                                language.addOfferLanguage(offerLanguage);
                                offerLanguage.setLanguage(language);
                            }
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("Language", offer.getId());
                        }

                        // nastaveni odpovidajicich pracovnich vztahu
                        // !! pracovnich vztahu muze byt vice polozek - ukladaji se jako M:N >> pomocna tabulka
                        JSONArray ships = (JSONArray) nextObject.get("pracovnePravniVztahy");
                        try {
                            Iterator<JSONObject> iteratorWorkShips = ships.iterator();
                            Set<Workship> workshipSet = new HashSet<Workship>();

                            while (iteratorWorkShips.hasNext()) {
                                JSONObject workShipObject = iteratorWorkShips.next();
                                workshipSet.add(workshipService.findWorkshipById(workShipObject.get("id").toString()));
                            }
                            offer.setWorkships(workshipSet);
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("WorkShip", offer.getId());
                        }

                        // nastaveni odpovidajicich vhodnosti
                        // !! vhodnosti muze byt vice polozek - ukladaji se jako M:N >> pomocna tabulka
                        JSONArray suitabilites = (JSONArray) nextObject.get("vhodnostiPracovnihoMista");
                        try {
                            Iterator<JSONObject> iteratorSuitabilites = suitabilites.iterator();
                            Set<Suitability> suitabilitySet = new HashSet<Suitability>();

                            while (iteratorSuitabilites.hasNext()) {
                                JSONObject suitabilityObject = iteratorSuitabilites.next();
                                suitabilitySet.add(suitabilityService.findSuitabilityById(suitabilityObject.get("id").toString()));
                            }
                            offer.setSuitabilities(suitabilitySet);
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("Suitability", offer.getId());
                        }

                        JSONObject place = (JSONObject) nextObject.get("mistoVykonuPrace");
                        try {
                            JSONObject placeType = (JSONObject) place.get("typMistaVykonuPrace");
                            offer.setPlaceType(placeTypeService.findPlaceTypeById(placeType.get("id").toString()));
                        } catch (java.lang.NullPointerException exception) {
                            logNPException("PlaceType", offer.getId());
                        }

                        // nastaveni mista pracoviste podle typu pracoviste, vcetne adresnich informaci
                        if ((offer.getPlaceType().getCode().equals("obec")) || (offer.getPlaceType().getCode().equals("adrvolna"))) {
                            WorkPlace workPlace = new WorkPlace();

                            JSONObject village = (JSONObject) place.get("obec");
                            try {
                                workPlace.setVillage(villageService.findVillageById(village.get("id").toString()));
                            } catch (java.lang.NullPointerException exception) {
                                logNPException("Village", offer.getId());
                            }

                            JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                            Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                            JSONObject pracovisteObject = iteratorPracoviste.next();

                            if (pracovisteObject.get("email") != null)
                                workPlace.setEmail(pracovisteObject.get("email").toString());
                            if (pracovisteObject.get("nazev") != null)
                                workPlace.setName(pracovisteObject.get("nazev").toString());
                            if (pracovisteObject.get("telefon") != null)
                                workPlace.setPhone(pracovisteObject.get("telefon").toString());
                            workPlaceService.saveWorkPlace(workPlace);

                            offer.setWorkPlace(workPlace);
                        } else if (offer.getPlaceType().getCode().equals("okres")) {
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
                            } catch (java.lang.NullPointerException exception) {
                                logNPException("Districts", offer.getId());
                            }

                            JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                            Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                            JSONObject pracovisteObject = iteratorPracoviste.next();

                            if (pracovisteObject.get("email") != null)
                                workPlace.setEmail(pracovisteObject.get("email").toString());
                            if (pracovisteObject.get("nazev") != null)
                                workPlace.setName(pracovisteObject.get("nazev").toString());
                            if (pracovisteObject.get("telefon") != null)
                                workPlace.setPhone(pracovisteObject.get("telefon").toString());
                            workPlaceService.saveWorkPlace(workPlace);

                            offer.setWorkPlace(workPlace);
                        } else if (offer.getPlaceType().getCode().equals("adrprov")) {
                            WorkPlace workPlace = new WorkPlace();

                            JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                            Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                            JSONObject pracovisteObject = iteratorPracoviste.next();

                            if (pracovisteObject.get("email") != null)
                                workPlace.setEmail(pracovisteObject.get("email").toString());
                            if (pracovisteObject.get("nazev") != null)
                                workPlace.setName(pracovisteObject.get("nazev").toString());
                            if (pracovisteObject.get("telefon") != null)
                                workPlace.setPhone(pracovisteObject.get("telefon").toString());

                            JSONObject addressObject = (JSONObject) pracovisteObject.get("adresa");
                            if (addressObject.get("cisloDomovni") != null)
                                workPlace.setHouseNumber(addressObject.get("cisloDomovni").toString());
                            if (addressObject.get("cisloOrientacni") != null)
                                workPlace.setOrientationNumber(addressObject.get("cisloOrientacni").toString());
                            if (addressObject.get("psc") != null)
                                workPlace.setZipCode(addressObject.get("psc").toString());

                            JSONObject streetObject = (JSONObject) addressObject.get("ulice");
                            if (streetObject != null)
                                workPlace.setStreet(streetObject.get("nazev").toString());

                            JSONObject villageObject = (JSONObject) addressObject.get("obec");
                            try {
                                workPlace.setVillage(villageService.findVillageById(villageObject.get("id").toString()));
                            } catch (java.lang.NullPointerException exception) {
                                logNPException("Village", offer.getId());
                            }

                            JSONObject villagePartObject = (JSONObject) addressObject.get("castObce");
                            try {
                                workPlace.setVillagePart(villagePartService.findVillagePartById(villagePartObject.get("id").toString()));
                            } catch (java.lang.NullPointerException exception) {
                                logNPException("VillagePart", offer.getId());
                            }

                            workPlaceService.saveWorkPlace(workPlace);

                            offer.setWorkPlace(workPlace);
                        } else if (offer.getPlaceType().getCode().equals("celaCR")) {
                            WorkPlace workPlace = new WorkPlace();

                            JSONArray pracoviste = (JSONArray) place.get("pracoviste");
                            Iterator<JSONObject> iteratorPracoviste = pracoviste.iterator();
                            JSONObject pracovisteObject = iteratorPracoviste.next();

                            if (pracovisteObject.get("email") != null)
                                workPlace.setEmail(pracovisteObject.get("email").toString());
                            if (pracovisteObject.get("nazev") != null)
                                workPlace.setName(pracovisteObject.get("nazev").toString());
                            if (pracovisteObject.get("telefon") != null)
                                workPlace.setPhone(pracovisteObject.get("telefon").toString());
                            workPlaceService.saveWorkPlace(workPlace);

                            offer.setWorkPlace(workPlace);
                        }
                        offerService.saveOffer(offer);
                        parsedOffersCount++;
                    }
                }
            } catch (java.lang.NullPointerException exception) {
                System.out.println("No offers found");
            }
        }
        // vynulovani progress hodnot
        resetOffersCounters();
        return true;
    }

    /**
     * Reseting of progress values for codebook data
     */
    private void resetCodebookCounters() {
        codebookParsedRecords = 0;
        codebookParsingProgress = 0;
    }

    /**
     * Reseting of progress values for offers data
     */
    private void resetOffersCounters() {
        parsedOffersCount = 1;
        actualParsedOfferInsertionDate = "zatím žádný";
        actualParsedOfferEditDate = "zatím žádný";
    }

    /**
     * Method lists in the console the log of objects that were identified as missing during the parsing process in the source JSON data - NULL
     *
     * @param targetObject - String - target object name/description
     * @param offerId      - Long - ID of offer
     */
    private void logNPException(String targetObject, Long offerId) {
        System.out.println("NullPointerException for target object: " + targetObject + " - these data was not set in parsed JSON file for offer with ID: " + offerId);
    }
}