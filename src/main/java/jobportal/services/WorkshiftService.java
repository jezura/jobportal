package jobportal.services;

import jobportal.dao.WorkshiftRepository;
import jobportal.models.offer_data_models.codebooks.Workshift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WorkshiftService {
    @Autowired
    private WorkshiftRepository workshiftRepository;

    public Collection<Workshift> findAllWorkshifts(){
        List<Workshift> workshifts = new ArrayList<Workshift>();
        for (Workshift workshift:workshiftRepository.findAll())
        {
            workshifts.add(workshift);
        }
        return workshifts;
    }

    public long getCount() {
        return workshiftRepository.count();
    }

    public Workshift findWorkshiftById(String id) {
        return workshiftRepository.findWorkshiftById(id);
    }

    public Workshift findWorkshiftByCode(String code) {
        return workshiftRepository.findWorkshiftByCode(code);
    }

    public void saveWorkshift(Workshift w){
        workshiftRepository.save(w);
    }
}