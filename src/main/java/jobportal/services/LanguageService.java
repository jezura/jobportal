package jobportal.services;

import jobportal.dao.LanguageRepository;
import jobportal.dao.SkillRepository;
import jobportal.models.Language;
import jobportal.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public Collection<Language> findAllLanguages(){
        List<Language> languages = new ArrayList<Language>();
        for (Language language:languageRepository.findAll())
        {
            languages.add(language);
        }
        return languages;
    }

    public Language findLanguageById(String id) {
        return languageRepository.findLanguageById(id);
    }

    public void saveLanguage(Language l){
        languageRepository.save(l);
    }
}