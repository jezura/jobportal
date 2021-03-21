package jobportal.services;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.EduGeneralField;
import jobportal.models.internal_models.codebooks.EduLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EduGeneralFieldService {
    @Autowired
    private EduGeneralFieldRepository eduGeneralFieldRepository;

    public Collection<EduGeneralField> findAllEduGeneralFields(){
        List<EduGeneralField> eduGeneralFields = new ArrayList<EduGeneralField>();
        for (EduGeneralField eduGeneralField:eduGeneralFieldRepository.findAll())
        {
            eduGeneralFields.add(eduGeneralField);
        }
        return eduGeneralFields;
    }

    public long getCount() {
        return eduGeneralFieldRepository.count();
    }

    public EduGeneralField findEduGeneralFieldByAnnCode(int annCode) {
        return eduGeneralFieldRepository.findEduGeneralFieldByAnnCode(annCode);
    }

    public EduGeneralField findEduGeneralFieldByName(String name) {
        return eduGeneralFieldRepository.findEduGeneralFieldByName(name);
    }

    public EduGeneralField findEduGeneralFieldById(int id) {
        return eduGeneralFieldRepository.findEduGeneralFieldById(id);
    }

    public void saveEduGeneralField(EduGeneralField e){
        eduGeneralFieldRepository.save(e);
    }
}