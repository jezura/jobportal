package jobportal.controllers;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.EduGeneralField;
import jobportal.models.internal_models.codebooks.EduLevel;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.models.internal_models.data_entites.FieldsRelevancy;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.models.offer_data_models.codebooks.Region;
import jobportal.services.*;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {
    private static final int pageSize = 20;
    private String message_notification = "";

    @Autowired
    RegionService regionService;
    @Autowired
    PersonService personService;
    @Autowired
    FieldsRelevancyService fieldsRelevancyService;
    @Autowired
    EduLevelService eduLevelService;
    @Autowired
    EduGeneralFieldService eduGeneralFieldService;
    @Autowired
    CzechNameService czechNameService;
    @Autowired
    TitleService titleService;
    @Autowired
    EduLevelRepository eduLevelRepository;
    @Autowired
    EduGeneralFieldRepository eduGeneralFieldRepository;


    @RequestMapping(value = "/admin/allUsers")
    public String showAdminAllUsers(Model model) {
        return showAdminAllUsersPageable(model, 1);
    }

    @GetMapping(value = "/admin/allUsers/page/{pageNumber}")
    public String showAdminAllUsersPageable(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<RegisteredUser> registeredUserPage = personService.findAllRegisteredUsersPageable(currentPage, pageSize);
        long totalUsers = registeredUserPage.getTotalElements();
        int totalPages = registeredUserPage.getTotalPages();
        int lastUserNum = currentPage * pageSize;
        int firstUserNum = lastUserNum - pageSize + 1;
        if (lastUserNum > totalUsers) {
            lastUserNum = (int) totalUsers;
        }
        long usersCount = personService.getRegisteredUsersCount();
        Collection<RegisteredUser> registeredUsers = registeredUserPage.getContent();
        Collection<Region> regions = regionService.findAllRegions();

        List<RelevanceScore> relevanceScores = new ArrayList<>();
        for (RegisteredUser regUser : registeredUsers) {
            relevanceScores.add(new RelevanceScore(fieldsRelevancyService.findFieldsRelevancyById(regUser.getFieldsRelevancy().getId()).getRelevanceScoresArray(), regUser.getId()));
        }

        model.addAttribute("registeredUsers", registeredUsers);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstUserNum", firstUserNum);
        model.addAttribute("lastUserNum", lastUserNum);
        model.addAttribute("usersCount", usersCount);
        model.addAttribute("regions", regions);
        model.addAttribute("relevanceScores", relevanceScores);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "adminAllUsers";
    }

    @PostMapping(value = "/admin/searchUsers")
    public String showFilteredUsers(Model model, @RequestParam(name = "emailSearch", required = false) String emailSearch,
                                    @RequestParam(name = "idSearch", required = false) String idSearch,
                                    @RequestParam(name = "fullNameSearch", required = false) String fullNameSearch,
                                    @RequestParam(name = "page", required = false) String page) {
        int currentPage;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        } else {
            currentPage = 1;
        }
        if ((emailSearch.isBlank()) && (idSearch.isBlank()) && (fullNameSearch.isBlank())) {
            return showAdminAllUsersPageable(model, 1);
        } else if (idSearch.isBlank()) {
            return showAdminFilteredUsersPageable(model, currentPage, 0, emailSearch, fullNameSearch);
        }
        return showAdminFilteredUsersPageable(model, currentPage, Integer.valueOf(idSearch), emailSearch, fullNameSearch);
    }

    @RequestMapping(value = "/emailsAutocomplete")
    @ResponseBody
    public List<String> autoEmail(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> emails = personService.findEmailsLikeSearchTerm(term);
        return emails;
    }

    @RequestMapping(value = "/fullNamesAutocomplete")
    @ResponseBody
    public List<String> autoFullName(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> fullNames = personService.findFullNamesLikeSearchTerm(term);
        return fullNames;
    }

    @GetMapping(value = "/admin/searchedUsers/page/{pageNumber}")
    public String showAdminFilteredUsersPageable(Model model, @PathVariable("pageNumber") int currentPage, int userId, String emailSearch, String fullNameSearch) {
        List<RegisteredUser> registeredUsers = new ArrayList<>();
        if (userId > 0) {
            RegisteredUser registeredUser = personService.findRegisteredUserById(userId);
            model.addAttribute("searchedId", userId);
            if (registeredUser != null) {
                registeredUsers.add(registeredUser);
            }
        } else {
            Page<RegisteredUser> registeredUserPage;
            if ((!emailSearch.isBlank()) && (!fullNameSearch.isBlank())) {
                registeredUserPage = personService.findRegisteredUsersByFirstNameLastNameAndEmailPageable(fullNameSearch, emailSearch, currentPage, pageSize);
                model.addAttribute("searchedEmail", emailSearch);
                model.addAttribute("searchedFullName", fullNameSearch);
            } else if (!emailSearch.isBlank()) {
                registeredUserPage = personService.findRegisteredUserByEmailLikePageable(emailSearch, currentPage, pageSize);
                model.addAttribute("searchedEmail", emailSearch);
            } else {
                registeredUserPage = personService.findRegisteredUsersByFirstNameLastNamePageable(fullNameSearch, currentPage, pageSize);
                model.addAttribute("searchedFullName", fullNameSearch);
            }
            long totalUsers = registeredUserPage.getTotalElements();
            int totalPages = registeredUserPage.getTotalPages();
            int lastUserNum = currentPage * pageSize;
            int firstUserNum = lastUserNum - pageSize + 1;
            if (lastUserNum > totalUsers) {
                lastUserNum = (int) totalUsers;
            }
            registeredUsers = registeredUserPage.getContent();

            model.addAttribute("firstUserNum", firstUserNum);
            model.addAttribute("lastUserNum", lastUserNum);
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalPages", totalPages);
        }

        long usersCount = personService.getRegisteredUsersCount();
        System.out.println("Registered users list size: " + registeredUsers.size());

        if (registeredUsers.size() > 0) {
            Collection<Region> regions = regionService.findAllRegions();
            List<RelevanceScore> relevanceScores = new ArrayList<>();
            for (RegisteredUser regUser : registeredUsers) {
                relevanceScores.add(new RelevanceScore(fieldsRelevancyService.findFieldsRelevancyById(regUser.getFieldsRelevancy().getId()).getRelevanceScoresArray(), regUser.getId()));
            }
            model.addAttribute("regions", regions);
            model.addAttribute("relevanceScores", relevanceScores);
        }
        model.addAttribute("registeredUsers", registeredUsers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("usersCount", usersCount);
        model.addAttribute("searching", true);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "adminAllUsers";
    }

    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {
        RegisteredUser registeredUser = new RegisteredUser();
        model.addAttribute("registeredUser", registeredUser);
        populateWithData(model);
        return "registration";
    }

    @PostMapping(value = "/quickRegistration")
    public String showQuickRegistrationForm(
            Model model,
            @RequestParam("file") MultipartFile[] files) throws IOException {
        RegisteredUser registeredUser = new RegisteredUser();

        CVProfile cvProfile = new CVProfile();
        CVExtractor cvExtractor = new CVExtractor(eduLevelRepository, eduGeneralFieldRepository);
        cvExtractor.processCvAndSetTextContentToExtractedTextVariable(files);

        //       >>> EXTRACTING PROCESS <<<
        cvProfile.buildCompleteCvProfile(cvExtractor, czechNameService.findAllCzechNames(), titleService.findAllTitles());
        registeredUser = cvProfile.preFillRegisteredUserAttributesWithIdentifiedInformation(registeredUser, eduLevelRepository, eduGeneralFieldRepository);

        model.addAttribute("registeredUser", registeredUser);
        populateWithData(model);

        return "quickRegistration";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("registeredUser") RegisteredUser registeredUser, BindingResult bindingResult,
                           @RequestParam("passwordCheck") String passwordCheck,
                           Model model) throws IOException {

        // check for email/login duplicity and if yes, add error
        if (!personService.isUnique(registeredUser.getEmail())) {
            FieldError error = new FieldError("registeredUser", "email",
                    "Zvolený email je již registrován s jiným existujícím účtem");
            bindingResult.addError(error);
        }

        // check if both passwords are equal, if not, add new error
        if (!(registeredUser.getPassword().equals(passwordCheck))) {
            FieldError error = new FieldError("registeredUser", "password",
                    "Zadaná hesla se neshodují, zkuste to prosím znovu");
            bindingResult.addError(error);
        }
        bindingResult.getErrorCount();

        // if there are some errors, return back to registration form and display errors to correct
        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "registration";
        }

        // if there are no errors, finish things to successfully crate user and then save
        registeredUser.setRole("REGISTERED_USER");
        String encodedPassword = new BCryptPasswordEncoder().encode(registeredUser.getPassword());
        registeredUser.setPassword(encodedPassword);
        FieldsRelevancy fr = new FieldsRelevancy();
        fr.setRelevancies(registeredUser);
        fieldsRelevancyService.saveFieldsRelevancy(fr);
        registeredUser.setFieldsRelevancy(fr);
        personService.saveRegisteredUser(registeredUser);
        return "redirect:/login";
    }

    @PostMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        personService.deleteRegisteredUser(id);
        message_notification = "Vybraný uživatelský účet byl úspěšně odstraněn";
        return "redirect:/admin/allUsers";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Zadaný přihlašovací email nebo heslo nejsou správné");
        return "login";
    }

    @GetMapping(value = "/registeredUser/accountOverview/{openModalChangePassword}")
    public String showAccountOverviewPage(@PathVariable(name = "openModalChangePassword") String openModalChangePassword, Model model) {
        System.out.println("Boolean openModalChangePassword: " + openModalChangePassword);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser;
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);
        RelevanceScore relevanceScore = new RelevanceScore();
        relevanceScore.setRelevanceScores(registeredUser.getFieldsRelevancy().getRelevanceScoresArray());
        model.addAttribute("registeredUser", registeredUser);
        model.addAttribute("relevanceScore", relevanceScore);
        model.addAttribute("openModalChangePassword", openModalChangePassword);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        populateWithData(model);
        return "registeredUser/accountOverview";
    }

    @RequestMapping(value = "/registeredUser/updateUser/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable(name = "id") int id,
                             @RequestParam("newLastName") String newLastName,
                             @RequestParam("newRegion") String newRegion,
                             @RequestParam("newEduLevel") String newEduLevel,
                             @RequestParam("newEduGeneralField") String newEduGeneralField
    ) throws IOException {

        int newEduLevelId = Integer.valueOf(newEduLevel);
        int newEduGeneralFieldId = Integer.valueOf(newEduGeneralField);

        RegisteredUser registeredUser = personService.findRegisteredUserById(id);

        if (!newLastName.equals(registeredUser.getLastName())) {
            String regexLastName = "^[a-zA-ZáéíóúůýčšžřÁÉÍÓÚŮÝČŠŽŘ´']{3,20}";
            Pattern pattern = Pattern.compile(regexLastName);
            Matcher matcher = pattern.matcher(newLastName);

            if (matcher.find()) {
                registeredUser.setLastName(newLastName);
            } else {
                message_notification = "Chyba: Nově zadané příjmení neodpovídá kritériím pro příjmení. Aktualizace se nezdařila";
                return "redirect:/registeredUser/accountOverview/modalFalse";
            }
        }

        if (!newRegion.equals(registeredUser.getRegion().getId())) {
            registeredUser.setRegion(regionService.findRegionById(newRegion));
        }

        if ((newEduLevelId != registeredUser.getEduLevel().getId()) || (newEduGeneralFieldId != registeredUser.getEduGeneralField().getId())) {
            if (newEduLevelId != registeredUser.getEduLevel().getId()) {
                registeredUser.setEduLevel(eduLevelService.findEduLevelById(newEduLevelId));
            }
            if (newEduGeneralFieldId != registeredUser.getEduGeneralField().getId()) {
                registeredUser.setEduGeneralField(eduGeneralFieldService.findEduGeneralFieldById(newEduGeneralFieldId));
            }

            // predict new work fields relevancies and update it
            int oldRelevaciesId = registeredUser.getFieldsRelevancy().getId();
            FieldsRelevancy fr = new FieldsRelevancy();
            fr.setRelevancies(registeredUser);
            fieldsRelevancyService.saveFieldsRelevancy(fr);
            registeredUser.setFieldsRelevancy(fr);

            //delete old fields relevancies from db
            fieldsRelevancyService.deleteFieldsRelevancy(oldRelevaciesId);
        }

        personService.saveRegisteredUser(registeredUser);
        message_notification = "Aktualizace osobních údajů proběhla úspěšně";
        return "redirect:/registeredUser/accountOverview/modalFalse";
    }

    @RequestMapping(value = "/registeredUser/changePassword/{userId}", method = RequestMethod.POST)
    public String changePassword(@PathVariable(name = "userId") int userId,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("newPasswordCheck") String newPasswordCheck) {

        RegisteredUser registeredUser = personService.findRegisteredUserById(userId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // if the current password is correct -> user is authenticated
        if(bCryptPasswordEncoder.matches(oldPassword, registeredUser.getPassword())){
            // if the both new passwords are equals
            if(newPassword.equals(newPasswordCheck)){
                // encrypt new password and update it in database
                registeredUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
                personService.saveRegisteredUser(registeredUser);
                message_notification = "Vaše heslo bylo úspěšně změněno";
                return "redirect:/registeredUser/accountOverview/modalFalse";
            }else{
                message_notification = "Chyba: Nově zadaná hesla se neshodují, zkuste to prosím znovu";
                return "redirect:/registeredUser/accountOverview/modalFalse";
            }
        }else{
            message_notification = "Chyba: Vaše dosavadní heslo bylo zadáno chybně, zkuste to prosím znovu";
            return "redirect:/registeredUser/accountOverview/modalFalse";
        }
    }

    private void populateWithData(Model model) {
        Collection<Region> regions = regionService.findAllRegions();
        Collection<EduLevel> eduLevels = eduLevelService.findAllEduLevels();
        Collection<EduGeneralField> eduGeneralFields = eduGeneralFieldService.findAllEduGeneralFields();
        model.addAttribute("regions", regions);
        model.addAttribute("eduLevels", eduLevels);
        model.addAttribute("eduGeneralFields", eduGeneralFields);
    }
}