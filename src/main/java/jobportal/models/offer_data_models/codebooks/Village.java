package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model class for Village codebook data entity.
 * An instance represents row in villages table.
 */
@Entity
@Table(name = "villages")
public class Village extends CodedEntity {

    @ManyToOne
    @NotNull(message = "Choose district")
    @JoinColumn(name = "district_id")
    private District district;

    public Village() {
    }

    public Village(@NotNull(message = "Choose district") District district) {
        this.district = district;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}