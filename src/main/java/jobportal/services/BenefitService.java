package jobportal.services;

import jobportal.dao.BenefitRepository;
import jobportal.dao.SkillRepository;
import jobportal.models.Benefit;
import jobportal.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BenefitService {
    @Autowired
    private BenefitRepository benefitRepository;

    public Collection<Benefit> findAllBenefits(){
        List<Benefit> benefits = new ArrayList<Benefit>();
        for (Benefit benefit:benefitRepository.findAll())
        {
            benefits.add(benefit);
        }
        return benefits;
    }

    public long getCount() {
        return benefitRepository.count();
    }

    public Benefit findBenefitById(String id) {
        return benefitRepository.findBenefitById(id);
    }

    public void saveBenefit(Benefit b){
        benefitRepository.save(b);
    }
}