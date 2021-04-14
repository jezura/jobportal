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

/**
 * Controller for managing data and actions related to users and user accounts
 */
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


    /**
     * Method redirects to showAdminAllUsersPageable() to display 1st page of registered users in administration
     * part od this app
     *
     * @param model - Model instance
     * @return - it will call showAdminAllUsersPageable(model, 1) method to display 1st page with users list
     */
    @RequestMapping(value = "/admin/allUsers")
    public String showAdminAllUsers(Model model) {
        return showAdminAllUsersPageable(model, 1);
    }

    /**
     * Method loads all registered users for selected page number and other needed data from repository
     * and then returns adminAllUsers.html template (displaying registered users list in administrator view mode)
     *
     * @param model       - Model instance
     * @param currentPage - current page number
     * @return String - "adminAllUsers.html" template name
     */
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

    /**
     * Method evaluates the received search parameters and decides to call another method showAdminFilteredUsersPageable or
     * showAdminAllUsersPageable
     *
     * @param model          - Model instance
     * @param emailSearch    - email searched value
     * @param idSearch       - user id searched value
     * @param fullNameSearch - user full name searched value
     * @param page           - page number
     * @return - it will call showAdminFilteredUsersPageable() or showAdminAllUsersPageable(model, 1) method
     */
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

    /**
     * Email search term AJAX autocomplete method
     *
     * @param term - searched user email term
     * @return - List of Strings - autocompleted/suggested emails from DB
     */
    @RequestMapping(value = "/emailsAutocomplete")
    @ResponseBody
    public List<String> autoEmail(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> emails = personService.findEmailsLikeSearchTerm(term);
        return emails;
    }

    /**
     * Full name search term AJAX autocomplete method
     *
     * @param term - searched user full name term
     * @return - List of Strings - autocompleted/suggested full names of registered users from DB
     */
    @RequestMapping(value = "/fullNamesAutocomplete")
    @ResponseBody
    public List<String> autoFullName(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> fullNames = personService.findFullNamesLikeSearchTerm(term);
        return fullNames;
    }

    /**
     * Search/filtering results for administrator..
     * Method loads all registered users meeting search and filter parameters for selected page number, and also other
     * needed data from repository and then returns adminAllUsers.html template (displaying filtered registered users
     * list in administrator view mode)
     *
     * @param model          - Model instance
     * @param currentPage    - current page number
     * @param userId         - user id search value
     * @param emailSearch    - user email search value
     * @param fullNameSearch - user full name search value
     * @return String - "adminAllUsers.html" template name
     */
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

    /**
     * New user standard registration..
     * Method creates new RegisteredUser instance and displays registration page for standard registration
     *
     * @param model - Model instance
     * @return String - "registration.html" template name
     */
    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {
        RegisteredUser registeredUser = new RegisteredUser();
        model.addAttribute("registeredUser", registeredUser);
        populateWithData(model);
        return "registration";
    }

    /**
     * New user QUICK registration..
     * Method creates new RegisteredUser instance and then runs extraction process on received
     * CV PDF/DOCX file using CVExtractor. Then, using the extracted information, builds the CVProfile object,
     * which is used to finally prefill RegisteredUser attributes with extracted information from CV.
     * Finally, this method displays quickRegistration page for QUICK registration
     * (page with prefilled registration form).
     *
     * @param model - Model instance
     * @param files - file with CV to extract information from it
     * @return String - "quickRegistration.html" template name
     * @throws IOException file exception
     */
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

    /**
     * Save user after filled/sent registration page form
     *
     * @param registeredUser - RegisteredUser instance
     * @param bindingResult  - BindingResult
     * @param passwordCheck  - second password to perform equality check
     * @param model          - Model instance
     * @return redirect to: /login IF success or display back registration.html page to correct errors
     * @throws IOException - exception
     */
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

    /**
     * Method will delete selected RegisteredUser by user id
     *
     * @param id - id of user to delete
     * @return redirect to: /admin/allUsers
     */
    @PostMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        personService.deleteRegisteredUser(id);
        message_notification = "Vybraný uživatelský účet byl úspěšně odstraněn";
        return "redirect:/admin/allUsers";
    }

    /**
     * Login page..
     * Method will display login page with login form
     *
     * @param model - Model instance
     * @param error - error message
     * @return String - "login.html" template name
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Zadaný přihlašovací email nebo heslo nejsou správné");
        return "login";
    }

    /**
     * Account overview..
     * Method load currently logged-in user and all his needed data and displays page with account information overview
     *
     * @param openModalChangePassword - auto open modal to change password?
     * @param model                   - Model instance
     * @return String - "accountOverview.html" template name
     */
    @GetMapping(value = "/registeredUser/accountOverview/{openModalChangePassword}")
    public String showAccountOverviewPage(@PathVariable(name = "openModalChangePassword") String openModalChangePassword, Model model) {
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

    /**
     * Update user data
     * Method updates selected users attributes and create new request for prediction service (if edu information changed)
     *
     * @param id                 - id of user to update
     * @param newLastName        - new last name to update
     * @param newRegion          - new region to update
     * @param newEduLevel        - new education level to update
     * @param newEduGeneralField - new education general field to update
     * @return redirect to: /registeredUser/accountOverview/modalFalse
     * @throws IOException exception
     */
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

            // predict new job fields relevancies and update it
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

    /**
     * Update users password
     * Method updates password for selected user
     *
     * @param userId           - user id to change password
     * @param oldPassword      - old password
     * @param newPassword      - new password
     * @param newPasswordCheck - second new password (to perform equality check)
     * @return - redirect to: /registeredUser/accountOverview/modalFalse
     */
    @RequestMapping(value = "/registeredUser/changePassword/{userId}", method = RequestMethod.POST)
    public String changePassword(@PathVariable(name = "userId") int userId,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("newPasswordCheck") String newPasswordCheck) {

        RegisteredUser registeredUser = personService.findRegisteredUserById(userId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // if the current password is correct -> user is authenticated
        if (bCryptPasswordEncoder.matches(oldPassword, registeredUser.getPassword())) {
            // if the both new passwords are equals
            if (newPassword.equals(newPasswordCheck)) {
                // encrypt new password and update it in database
                registeredUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
                personService.saveRegisteredUser(registeredUser);
                message_notification = "Vaše heslo bylo úspěšně změněno";
                return "redirect:/registeredUser/accountOverview/modalFalse";
            } else {
                message_notification = "Chyba: Nově zadaná hesla se neshodují, zkuste to prosím znovu";
                return "redirect:/registeredUser/accountOverview/modalFalse";
            }
        } else {
            message_notification = "Chyba: Vaše dosavadní heslo bylo zadáno chybně, zkuste to prosím znovu";
            return "redirect:/registeredUser/accountOverview/modalFalse";
        }
    }

    /**
     * Method will populate model instance with needed codebook data
     *
     * @param model - Model instance
     */
    private void populateWithData(Model model) {
        Collection<Region> regions = regionService.findAllRegions();
        Collection<EduLevel> eduLevels = eduLevelService.findAllEduLevels();
        Collection<EduGeneralField> eduGeneralFields = eduGeneralFieldService.findAllEduGeneralFields();
        model.addAttribute("regions", regions);
        model.addAttribute("eduLevels", eduLevels);
        model.addAttribute("eduGeneralFields", eduGeneralFields);
    }
}