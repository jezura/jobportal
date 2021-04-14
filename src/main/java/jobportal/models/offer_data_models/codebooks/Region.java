package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Model class for Region codebook data entity.
 * An instance represents row in regions table.
 */
@Entity
@Table(name = "regions")
public class Region extends CodedEntity {

    @Column(name = "kodNuts3")
    @Size(min = 2, max = 32, message = "kodNuts3 must be between 2 and 32 characters long")
    private String kodNuts3;

    public Region() {
    }

    public Region(@Size(min = 2, max = 32, message = "kodNuts3 must be between 2 and 32 characters long") String kodNuts3) {
        this.kodNuts3 = kodNuts3;
    }

    public String getKodNuts3() {
        return kodNuts3;
    }

    public void setKodNuts3(String kodNuts3) {
        this.kodNuts3 = kodNuts3;
    }
}