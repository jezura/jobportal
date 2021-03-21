package jobportal.services;

import jobportal.dao.FieldsRelevancyRepository;
import jobportal.models.internal_models.data_entites.FieldsRelevancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FieldsRelevancyService {
    @Autowired
    private FieldsRelevancyRepository fieldsRelevancyRepository;

    public Collection<FieldsRelevancy> findAllFieldsRelevancies(){
        List<FieldsRelevancy> fieldsRelevancies = new ArrayList<FieldsRelevancy>();
        for (FieldsRelevancy fr:fieldsRelevancyRepository.findAll())
        {
            fieldsRelevancies.add(fr);
        }
        return fieldsRelevancies;
    }

    public long getCount() {
        return fieldsRelevancyRepository.count();
    }

    public FieldsRelevancy findFieldsRelevancyById(int id) {
        return fieldsRelevancyRepository.findFieldsRelevancyById(id);
    }

    public void saveFieldsRelevancy(FieldsRelevancy fr){
        fieldsRelevancyRepository.save(fr);
    }

    public void deleteFieldsRelevancy(int id){
        fieldsRelevancyRepository.deleteById(id);
    }
}