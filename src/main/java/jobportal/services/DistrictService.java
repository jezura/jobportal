package jobportal.services;

import jobportal.dao.DistrictRepository;
import jobportal.models.offer_data_models.codebooks.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    public Collection<District> findAllDistricts(){
        List<District> districts = new ArrayList<District>();
        for (District district :districtRepository.findAll())
        {
            districts.add(district);
        }
        return districts;
    }

    public long getCount() {
        return districtRepository.count();
    }

    public District findDistrictById(String id) {
        return districtRepository.findDistrictById(id);
    }

    public void saveDistrict(District d){
        districtRepository.save(d);
    }

}