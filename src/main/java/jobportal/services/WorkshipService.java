package jobportal.services;

import jobportal.dao.WorkshipRepository;
import jobportal.models.offer_data_models.codebooks.Workship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WorkshipService {
    @Autowired
    private WorkshipRepository workshipRepository;

    public Collection<Workship> findAllWorkships(){
        List<Workship> workships = new ArrayList<Workship>();
        for (Workship workship:workshipRepository.findAll())
        {
            workships.add(workship);
        }
        return workships;
    }

    public long getCount() {
        return workshipRepository.count();
    }

    public Workship findWorkshipById(String id) {
        return workshipRepository.findWorkshipById(id);
    }

    public Workship findWorkshipByCode(String code) {
        return workshipRepository.findWorkshipByCode(code);
    }

    public void saveWorkship(Workship w){
        workshipRepository.save(w);
    }
}