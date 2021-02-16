package jobportal.services;

import jobportal.dao.SkillRepository;
import jobportal.models.offer_data_models.codebooks.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    public Collection<Skill> findAllSkills(){
        List<Skill> skills = new ArrayList<Skill>();
        for (Skill skill:skillRepository.findAll())
        {
            skills.add(skill);
        }
        return skills;
    }

    public long getCount() {
        return skillRepository.count();
    }

    public Skill findSkillById(String id) {
        return skillRepository.findSkillById(id);
    }

    public void saveSkill(Skill s){
        skillRepository.save(s);
    }
}