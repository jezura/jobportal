package jobportal.services;

import jobportal.dao.FieldRepository;
import jobportal.dao.ProfessionRepository;
import jobportal.models.Field;
import jobportal.models.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;

    public Collection<Profession> findAllProfessions(){
        List<Profession> professions = new ArrayList<Profession>();
        for (Profession profession:professionRepository.findAll())
        {
            professions.add(profession);
        }
        return professions;
    }

    public long getCount() {
        return professionRepository.count();
    }

    public Profession findProfessionById(String id) {
        return professionRepository.findProfessionById(id);
    }

    public Profession findProfessionByCode(String code) {
        return professionRepository.findProfessionByCode(code);
    }

    public void saveProfession(Profession p){
        professionRepository.save(p);
    }
}