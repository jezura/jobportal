package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Model class for Profession codebook data entity.
 * An instance represents row in professions table.
 */
@Entity
@Table(name = "professions")
public class Profession extends CodedEntity {

    @ManyToMany
    @JoinTable(name = "profession_field",
            joinColumns = @JoinColumn(name = "profession_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id"))
    private Set<Field> fields;

    public Profession() {
    }

    public Profession(Set<Field> fields) {
        this.fields = fields;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }
}