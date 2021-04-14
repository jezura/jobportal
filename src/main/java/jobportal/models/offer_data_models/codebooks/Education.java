package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model class for Education codebook data entity.
 * An instance represents row in educations table.
 */
@Entity
@Table(name = "educations")
public class Education extends CodedEntity {

    public Education() {
    }
}