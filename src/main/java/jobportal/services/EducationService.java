package jobportal.services;

import jobportal.dao.EducationRepository;
import jobportal.dao.SkillRepository;
import jobportal.models.Education;
import jobportal.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.EndDocument;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EducationService {
    @Autowired
    private EducationRepository educationRepository;

    public Collection<Education> findAllEducations(){
        List<Education> educations = new ArrayList<Education>();
        for (Education education:educationRepository.findAll())
        {
            educations.add(education);
        }
        return educations;
    }

    public long getCount() {
        return educationRepository.count();
    }

    public Education findEducationById(String id) {
        return educationRepository.findEducationById(id);
    }

    public Education findEducationByCode(String code) {
        return educationRepository.findEducationByCode(code);
    }

    public void saveEducation(Education e){
        educationRepository.save(e);
    }
}