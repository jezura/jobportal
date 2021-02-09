package jobportal.services;

import jobportal.dao.RegionRepository;
import jobportal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public Collection<Region> findAllRegions(){
        List<Region> regions = new ArrayList<Region>();
        for (Region region :regionRepository.findAll())
        {
            regions.add(region);
        }
        return regions;
    }

    public long getCount() {
        return regionRepository.count();
    }

    public Region findRegionById(String id) {
        return regionRepository.findRegionById(id);
    }

    public void saveRegion(Region r){
        regionRepository.save(r);
    }

}