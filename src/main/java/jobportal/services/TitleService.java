package jobportal.services;

import jobportal.dao.TitleRepository;
import jobportal.models.internal_models.codebooks.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TitleService {
    @Autowired
    private TitleRepository titleRepository;

    public Collection<Title> findAllTitles(){
        List<Title> titles = new ArrayList<Title>();
        for (Title title :titleRepository.findAll())
        {
            titles.add(title);
        }
        return titles;
    }

    public Title findTitleById(int id) {
        return titleRepository.findTitleById(id);
    }

    public Title findTitleByTitleVariant(String titleVariant) {
        return titleRepository.findTitleByTitleVariant(titleVariant);
    }

    public void saveTitle(Title t){
        titleRepository.save(t);
    }
}