package jobportal.controllers;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.cv_support.CVProfile;
import jobportal.models.internal_models.cv_support.EduLog;
import jobportal.models.internal_models.cv_support.RelevanceScore;
import jobportal.services.CzechNameService;
import jobportal.services.TitleService;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Controller for demo part of web app - demo environment for testing extraction and prediction functions..
 */
@Controller
public class CVController {
    @Autowired
    private CzechNameService czechNameService;
    @Autowired
    private TitleService titleService;
    @Autowired
    private EduLevelRepository eduLevelRepository;
    @Autowired
    private EduGeneralFieldRepository eduGeneralFieldRepository;

    /**
     * Method shows demoIndex.html page
     *
     * @return String "demoIndex" template name
     */
    @RequestMapping(value = "/demo")
    public String showDemoIndex() {
        return "demoIndex";
    }

    /**
     * Method receives the PDF/DOCX file, extracts the text from it,
     * identifies the selected information in the text, and extracts it.
     * Then, using the extracted information, builds the CVProfile object,
     * which is used to finally obtain (from an external Python prediction service)
     * the predicted scores of relevances for job fields.
     * Method returns demoExtractedCVProfile.html template with results to display.
     *
     * @param model - Model object
     * @param files - MultipartFile[] files
     * @return "demoExtractedCVProfile" template with extraction and prediction results.
     * @throws IOException - file exception
     */
    @PostMapping(value = "/demo/processCv")
    public String processCV(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {
        EduLog eduLog = new EduLog();
        CVExtractor cvExtractor = new CVExtractor(eduLog, eduLevelRepository, eduGeneralFieldRepository);
        CVProfile cvProfile = new CVProfile();
        RelevanceScore relevanceScore = new RelevanceScore();

        // extract all text content from file to single String variable
        cvExtractor.processCvAndSetTextContentToExtractedTextVariable(files);

        // identify and extract all information from text and create new CVProfile object
        cvProfile.buildCompleteCvProfile(cvExtractor, czechNameService.findAllCzechNames(), titleService.findAllTitles());

        // get predicted relevances for created CVProfile
        relevanceScore.getPredictions(cvProfile);

        model.addAttribute("cvProfile", cvProfile);
        model.addAttribute("relevanceScore", relevanceScore);
        model.addAttribute("eduLog", cvExtractor.getEduLog());

        return "demoExtractedCVProfile";
    }
}