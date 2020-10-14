package jobportal.services;

import jobportal.dao.WorkPlaceRepository;
import jobportal.dao.WorkshiftRepository;
import jobportal.models.WorkPlace;
import jobportal.models.Workshift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WorkPlaceService {
    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    public Collection<WorkPlace> findAllWorkPlaces(){
        List<WorkPlace> workPlaces = new ArrayList<WorkPlace>();
        for (WorkPlace workPlace:workPlaceRepository.findAll())
        {
            workPlaces.add(workPlace);
        }
        return workPlaces;
    }

    public WorkPlace findWorkPlaceById(String id) {
        return workPlaceRepository.findWorkPlaceById(id);
    }

    public void saveWorkPlace(WorkPlace wp){
        workPlaceRepository.save(wp);
    }
}