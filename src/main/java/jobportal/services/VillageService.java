package jobportal.services;

import jobportal.dao.VillageRepository;
import jobportal.models.Village;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class VillageService {
    @Autowired
    private VillageRepository villageRepository;

    public Collection<Village> findAllVillages(){
        List<Village> villages = new ArrayList<Village>();
        for (Village village : villageRepository.findAll())
        {
            villages.add(village);
        }
        return villages;
    }

    public long getCount() {
        return villageRepository.count();
    }

    public Village findVillageById(String id) {
        return villageRepository.findVillageById(id);
    }

    public Village findVillageByCode(String code) {
        return villageRepository.findVillageByCode(code);
    }

    public void saveVillage(Village v){
        villageRepository.save(v);
    }
}