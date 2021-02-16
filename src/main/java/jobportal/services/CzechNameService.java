package jobportal.services;

import jobportal.dao.CzechNameRepository;
import jobportal.models.internal_models.cv_support.CzechName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CzechNameService {
    @Autowired
    private CzechNameRepository czechNameRepository;

    public Collection<CzechName> findAllCzechNames(){
        List<CzechName> czechNames = new ArrayList<CzechName>();
        for (CzechName czechName :czechNameRepository.findAll())
        {
            czechNames.add(czechName);
        }
        return czechNames;
    }

    public CzechName findCzechNameById(int id) {
        return czechNameRepository.findCzechNameById(id);
    }

    public CzechName findCzechNameByName(String firstName) {
        return czechNameRepository.findCzechNameByName(firstName);
    }

    public void saveCzechName(CzechName cn){
        czechNameRepository.save(cn);
    }
}