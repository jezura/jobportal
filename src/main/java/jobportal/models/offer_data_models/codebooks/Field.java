package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for Field codebook data entity.
 * An instance represents row in fields table.
 */
@Entity
@Table(name = "fields")
public class Field extends CodedEntity {

    @ManyToMany(mappedBy = "fields")
    private Set<Profession> professions = new HashSet<>();

    public Field() {
    }

    public Field(Set<Profession> professions) {
        this.professions = professions;
    }

    public Set<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(Set<Profession> professions) {
        this.professions = professions;
    }
}