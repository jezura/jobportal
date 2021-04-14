package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for Skill codebook data entity.
 * An instance represents row in skills table.
 */
@Entity
@Table(name = "skills")
public class Skill extends CodedEntity {
    @OneToMany(mappedBy = "skill")
    private Set<OfferSkill> offerSkills = new HashSet<OfferSkill>();

    public Skill() {
    }

    public Set<OfferSkill> getOfferSkills() {
        return offerSkills;
    }

    public void setOfferSkills(Set<OfferSkill> offerSkills) {
        this.offerSkills = offerSkills;
    }

    public void addOfferSkill(OfferSkill offerSkill) {
        this.offerSkills.add(offerSkill);
    }
}
