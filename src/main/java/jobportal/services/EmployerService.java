package jobportal.services;

import jobportal.dao.EmployerRepository;
import jobportal.models.offer_data_models.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public Collection<Employer> findAllEmployers(){
        List<Employer> employers = new ArrayList<Employer>();
        for (Employer employer :employerRepository.findAll())
        {
            employers.add(employer);
        }
        return employers;
    }

    public Employer findEmployerByIco(int ico) {
        return employerRepository.findEmployerByIco(ico);
    }

    public void saveEmployer(Employer e){
        employerRepository.save(e);
    }

}