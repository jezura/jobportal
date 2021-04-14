package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model class for VillagePart codebook data entity.
 * An instance represents row in village_parts table.
 */
@Entity
@Table(name = "village_parts")
public class VillagePart extends CodedEntity {

    public VillagePart() {
    }
}