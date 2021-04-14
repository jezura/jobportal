package jobportal.services;

import jobportal.dao.FirstContactRepository;
import jobportal.models.offer_data_models.FirstContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FirstContactService {
    @Autowired
    private FirstContactRepository firstContactRepository;

    public Collection<FirstContact> findAllFirstContacts(){
        List<FirstContact> firstContacts = new ArrayList<FirstContact>();
        for (FirstContact firstContact :firstContactRepository.findAll())
        {
            firstContacts.add(firstContact);
        }
        return firstContacts;
    }

    public FirstContact findFirstContactById(int id) {
        return firstContactRepository.findFirstContactById(id);
    }

    public void saveFirstContact(FirstContact fc){
        firstContactRepository.save(fc);
    }

    public void deleteFirstContact(FirstContact fc){
        firstContactRepository.delete(fc);
    }

}