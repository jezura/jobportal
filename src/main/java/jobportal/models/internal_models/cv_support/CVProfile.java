package jobportal.models.internal_models.cv_support;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.CzechName;
import jobportal.models.internal_models.codebooks.Title;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.utils.CVExtractor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

/**
 * CVProfile class was created mainly to store all extracted CV owner information in structural way.
 * So this class is mainly data store oriented and represents specific CV.
 * CVProfile object is a standard input for further work with extracted information - especially the prediction
 * of relevance scores for job fields or quick registration SMART function.
 * CVProfile is also responsible to prefill available RegisteredUser
 * information when quick registration using CV is selected.
 */
public class CVProfile {
    private String firstName;
    private String lastName;
    private String gender;
    private List<Title> titleList;
    private LocalDate birthDate;
    private int birthYear;
    private int age;
    private String email;
    private String mobile;
    private MaxEducation maxEducation;

    public CVProfile() {
    }

    public CVProfile(String firstName, String lastName, String gender, List<Title> titleList, LocalDate birthDate,
                     int birthYear, int age, String email, String mobile, MaxEducation maxEducation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.titleList = titleList;
        this.birthDate = birthDate;
        this.birthYear = birthYear;
        this.age = age;
        this.email = email;
        this.mobile = mobile;
        this.maxEducation = maxEducation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Title> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public MaxEducation getMaxEducation() {
        return maxEducation;
    }

    public void setMaxEducation(MaxEducation maxEducation) {
        this.maxEducation = maxEducation;
    }

    /**
     * Method create/build complete CVProfile object using given CVExtractor instance and other params
     *
     * @param cvExtractor   - CVExtractor instance
     * @param allCzechNames - Collection of all most common czech names from database
     * @param allTitles     - Collection of all most common academic titles from database
     */
    public void buildCompleteCvProfile(CVExtractor cvExtractor, Collection<CzechName> allCzechNames, Collection<Title> allTitles) {
        // extracting information from CV file using given CVExtractor instance
        CzechName extractedFirstName = cvExtractor.extractFirstName(allCzechNames, 40);
        setFirstName(extractedFirstName.getName());
        setGender(extractedFirstName.getGender());
        setLastName(cvExtractor.extractLastName(extractedFirstName.getName()));
        setTitleList(cvExtractor.extractTitle(allTitles));
        setEmail(cvExtractor.extractEmail());
        setMobile(cvExtractor.extractMobile());
        setMaxEducation(cvExtractor.extractMaxEducationAndGeneralEduField(getTitleList()));

        // extract and set age and birthDate or birthYear using given CVExtractor instance
        LocalDate extractedBirthDate = cvExtractor.extractBirthDate();
        if (extractedBirthDate != null) {
            long years;
            if (extractedBirthDate.getYear() < 1950) {
                extractedBirthDate = extractedBirthDate.plusYears(100);
                setBirthYear(extractedBirthDate.getYear());
            } else {
                setBirthDate(extractedBirthDate);
            }
            years = ChronoUnit.YEARS.between(extractedBirthDate, LocalDate.now());
            setAge((int) years);
        }
    }

    /**
     * Method is responsible to prefill all available RegisteredUser information when quick registration using CV
     * is selected
     *
     * @param registeredUser            - instance of new RegisteredUser to register
     * @param eduLevelRepository        - repository of education levels codebook data
     * @param eduGeneralFieldRepository - repository of education general fields codebook data
     * @return RegisteredUser object with prefilled available information
     */
    public RegisteredUser preFillRegisteredUserAttributesWithIdentifiedInformation(
            RegisteredUser registeredUser,
            EduLevelRepository eduLevelRepository,
            EduGeneralFieldRepository eduGeneralFieldRepository) {
        if (!getFirstName().isBlank()) {
            registeredUser.setFirstName(getFirstName());
        }
        if (!getLastName().isBlank()) {
            registeredUser.setLastName(getLastName());
        }
        if (!getEmail().isBlank()) {
            registeredUser.setEmail(getEmail());
        }
        if (!getGender().isBlank()) {
            registeredUser.setGender(getGender());
        }

        if (getBirthDate() != null) {
            registeredUser.setBirthYear(getBirthDate().getYear());
        } else if (getBirthYear() > 0) {
            registeredUser.setBirthYear(getBirthYear());
        }

        if (!getMaxEducation().getMaxEduLvl().getEduLevel().getName().isBlank()) {
            registeredUser.setEduLevel(eduLevelRepository.findEduLevelByName(getMaxEducation().getMaxEduLvl().getEduLevel().getName()));
        }
        if (!getMaxEducation().getEduGeneralField().getName().isBlank()) {
            registeredUser.setEduGeneralField(eduGeneralFieldRepository.findEduGeneralFieldByName(getMaxEducation().getEduGeneralField().getName()));
        }
        return registeredUser;
    }
}