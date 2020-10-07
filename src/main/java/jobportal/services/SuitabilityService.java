package jobportal.services;

import jobportal.dao.SuitabilityRepository;
import jobportal.dao.WorkshipRepository;
import jobportal.models.Suitability;
import jobportal.models.Workship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SuitabilityService {
    @Autowired
    private SuitabilityRepository suitabilityRepository;

    public Collection<Suitability> findAllSuitabilites(){
        List<Suitability> suitabilities = new ArrayList<Suitability>();
        for (Suitability suitability:suitabilityRepository.findAll())
        {
            suitabilities.add(suitability);
        }
        return suitabilities;
    }

    public Suitability findSuitabilityById(String id) {
        return suitabilityRepository.findSuitabilityById(id);
    }

    public Suitability findSuitabilityByCode(String code) {
        return suitabilityRepository.findSuitabilityByCode(code);
    }

    public void saveSuitability(Suitability s){
        suitabilityRepository.save(s);
    }
}