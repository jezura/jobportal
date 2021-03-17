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
    EduLevelRepository eduLevelRepository;
    @Autowired
    EduGeneralFieldRepository eduGeneralFieldRepository;
    @Autowired
    CzechNameService czechNameService;
    @Autowired
    TitleService titleService;


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
        if(lastUserNum > totalUsers) {
            lastUserNum = (int) totalUsers;
        }
        long usersCount = personService.getRegisteredUsersCount();
        Collection<RegisteredUser> registeredUsers = registeredUserPage.getContent();
        Collection<Region> regions = regionService.findAllRegions();

        List<RelevanceScore> relevanceScores = new ArrayList<>();
        for(RegisteredUser regUser: registeredUsers) {
            relevanceScores.add(new RelevanceScore(fieldsRelevancyService.findFieldsRelevancyById(regUser.getFieldsRelevancy().getId()).getRelevanceScoresArray(),regUser.getId()));
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
                                    @RequestParam(name = "page", required = false) String page){
        int currentPage;
        if(page != null) {
            currentPage = Integer.valueOf(page);
        }else{
            currentPage=1;
        }
       if((emailSearch.isBlank()) && (idSearch.isBlank()) && (fullNameSearch.isBlank())) {
            return showAdminAllUsersPageable(model, 1);
       }else if(idSearch.isBlank()){
           return showAdminFilteredUsersPageable(model, currentPage, 0, emailSearch, fullNameSearch);
       }
        return showAdminFilteredUsersPageable(model, currentPage, Integer.valueOf(idSearch), emailSearch, fullNameSearch);
    }

    @RequestMapping(value = "/emailsAutocomplete")
    @ResponseBody
    public List<String> autoEmail(@RequestParam(value = "term", required = false, defaultValue = "")String term){
        List<String> emails = personService.findEmailsLikeSearchTerm(term);
        return emails;
    }

    @RequestMapping(value = "/fullNamesAutocomplete")
    @ResponseBody
    public List<String> autoFullName(@RequestParam(value = "term", required = false, defaultValue = "")String term){
        List<String> fullNames = personService.findFullNamesLikeSearchTerm(term);
        return fullNames;
    }

    @GetMapping(value = "/admin/searchedUsers/page/{pageNumber}")
    public String showAdminFilteredUsersPageable(Model model, @PathVariable("pageNumber") int currentPage, int userId, String emailSearch, String fullNameSearch) {
        List<RegisteredUser> registeredUsers = new ArrayList<>();
        if(userId > 0){
            RegisteredUser registeredUser = personService.findRegisteredUserById(userId);
            model.addAttribute("searchedId", userId);
            if(registeredUser != null){
                registeredUsers.add(registeredUser);
            }
        }else{
            Page<RegisteredUser> registeredUserPage;
            if((!emailSearch.isBlank()) && (!fullNameSearch.isBlank())){
                registeredUserPage = personService.findRegisteredUsersByFirstNameLastNameAndEmailPageable(fullNameSearch, emailSearch, currentPage, pageSize);
                model.addAttribute("searchedEmail", emailSearch);
                model.addAttribute("searchedFullName", fullNameSearch);
            }else if (!emailSearch.isBlank()) {
                registeredUserPage = personService.findRegisteredUserByEmailLikePageable(emailSearch, currentPage, pageSize);
                model.addAttribute("searchedEmail", emailSearch);
            }else{
                registeredUserPage = personService.findRegisteredUsersByFirstNameLastNamePageable(fullNameSearch, currentPage, pageSize);
                model.addAttribute("searchedFullName", fullNameSearch);
            }
            long totalUsers = registeredUserPage.getTotalElements();
            int totalPages = registeredUserPage.getTotalPages();
            int lastUserNum = currentPage * pageSize;
            int firstUserNum = lastUserNum - pageSize + 1;
            if(lastUserNum > totalUsers) {
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

        if(registeredUsers.size() > 0) {
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
        if(!personService.isUnique(registeredUser.getEmail())){
            FieldError error = new FieldError("registeredUser", "email",
                    "Zvolený email je již registrován s jiným existujícím účtem");
            bindingResult.addError(error);
        }

        // check if both passwords are equal, if not, add new error
        if(!(registeredUser.getPassword().equals(passwordCheck))){
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

    @GetMapping(value = "/accountOverview")
    public String showAccountOverviewPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser;
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        registeredUser = personService.findRegisteredUserByEmail(email);
        model.addAttribute("registeredUser", registeredUser);
        populateWithData(model);
        return "accountOverview";
    }

    private void populateWithData(Model model){
        Collection<Region> regions = regionService.findAllRegions();
        Collection<EduLevel> eduLevels = (Collection<EduLevel>) eduLevelRepository.findAll();
        Collection<EduGeneralField> eduGeneralFields = (Collection<EduGeneralField>) eduGeneralFieldRepository.findAll();
        model.addAttribute("regions", regions);
        model.addAttribute("eduLevels", eduLevels);
        model.addAttribute("eduGeneralFields", eduGeneralFields);
    }
}