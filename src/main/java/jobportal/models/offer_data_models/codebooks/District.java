package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;
import jobportal.models.offer_data_models.WorkPlace;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for District codebook data entity.
 * An instance represents row in districts table.
 */
@Entity
@Table(name = "districts")
public class District extends CodedEntity {

    @ManyToMany(mappedBy = "districts")
    private Set<WorkPlace> workPlaces = new HashSet<>();

    @ManyToOne
    @NotNull(message = "Choose region")
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "kodLau")
    @Size(min = 2, max = 32, message = "kodLau must be between 2 and 32 characters long")
    private String kodLau;

    public District() {
    }

    public District(Set<WorkPlace> workPlaces, @NotNull(message = "Choose region") Region region, @Size(min = 2, max = 32, message = "kodLau must be between 2 and 32 characters long") String kodLau) {
        this.workPlaces = workPlaces;
        this.region = region;
        this.kodLau = kodLau;
    }

    public Set<WorkPlace> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(Set<WorkPlace> workPlaces) {
        this.workPlaces = workPlaces;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getKodLau() {
        return kodLau;
    }

    public void setKodLau(String kodLau) {
        this.kodLau = kodLau;
    }
}