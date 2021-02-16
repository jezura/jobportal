package jobportal.services;

import jobportal.dao.FieldRepository;
import jobportal.models.offer_data_models.codebooks.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;

    public Collection<Field> findAllFields(){
        List<Field> fields = new ArrayList<Field>();
        for (Field field:fieldRepository.findAll())
        {
            fields.add(field);
        }
        return fields;
    }

    public long getCount() {
        return fieldRepository.count();
    }

    public Field findFieldById(String id) {
        return fieldRepository.findFieldById(id);
    }

    public void saveField(Field f){
        fieldRepository.save(f);
    }
}