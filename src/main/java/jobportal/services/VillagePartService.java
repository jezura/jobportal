package jobportal.services;

import jobportal.dao.VillagePartRepository;
import jobportal.dao.VillageRepository;
import jobportal.models.Village;
import jobportal.models.VillagePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class VillagePartService {
    @Autowired
    private VillagePartRepository villagePartRepository;

    public Collection<VillagePart> findAllVillageParts(){
        List<VillagePart> villageParts = new ArrayList<VillagePart>();
        for (VillagePart villagePart :villagePartRepository.findAll())
        {
            villageParts.add(villagePart);
        }
        return villageParts;
    }

    public long getCount() {
        return villagePartRepository.count();
    }

    public VillagePart findVillagePartById(String id) {
        return villagePartRepository.findVillagePartById(id);
    }

    public VillagePart findVillagePartByCode(String code) {
        return villagePartRepository.findVillagePartByCode(code);
    }

    public void saveVillagePart(VillagePart vp){
        villagePartRepository.save(vp);
    }
}