package jobportal.services;

import jobportal.dao.PlaceTypeRepository;
import jobportal.dao.SkillRepository;
import jobportal.models.PlaceType;
import jobportal.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PlaceTypeService {
    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    public Collection<PlaceType> findAllPlaceTypes(){
        List<PlaceType> placeTypes = new ArrayList<PlaceType>();
        for (PlaceType placeType:placeTypeRepository.findAll())
        {
            placeTypes.add(placeType);
        }
        return placeTypes;
    }

    public long getCount() {
        return placeTypeRepository.count();
    }

    public PlaceType findPlaceTypeById(String id) {
        return placeTypeRepository.findPlaceTypeById(id);
    }

    public void savePlaceType(PlaceType pt){
        placeTypeRepository.save(pt);
    }
}