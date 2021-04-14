package jobportal.services;

import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.EduLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EduLevelService {
    @Autowired
    private EduLevelRepository eduLevelRepository;

    public Collection<EduLevel> findAllEduLevels(){
        List<EduLevel> eduLevels = new ArrayList<EduLevel>();
        for (EduLevel eduLevel:eduLevelRepository.findAll())
        {
            eduLevels.add(eduLevel);
        }
        return eduLevels;
    }

    public long getCount() {
        return eduLevelRepository.count();
    }

    public EduLevel findEduLevelByAnnCode(int annCode) {
        return eduLevelRepository.findEduLevelByAnnCode(annCode);
    }

    public EduLevel findEduLevelByName(String name) {
        return eduLevelRepository.findEduLevelByName(name);
    }

    public EduLevel findEduLevelById(int id) {
        return eduLevelRepository.findEduLevelById(id);
    }

    public void saveEduLevel(EduLevel e){
        eduLevelRepository.save(e);
    }
}