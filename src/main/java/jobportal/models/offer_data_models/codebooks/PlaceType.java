package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model class for PlaceType codebook data entity.
 * An instance represents row in place_types table.
 */
@Entity
@Table(name = "place_types")
public class PlaceType extends CodedEntity {

    public PlaceType() {
    }
}
