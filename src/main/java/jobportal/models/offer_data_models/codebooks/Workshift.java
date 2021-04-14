package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model class for Workshift codebook data entity.
 * An instance represents row in workshifts table.
 */
@Entity
@Table(name = "workshifts")
public class Workshift extends CodedEntity {

    public Workshift() {
    }
}