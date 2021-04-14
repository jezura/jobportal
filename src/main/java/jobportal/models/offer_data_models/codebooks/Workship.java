package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;
import jobportal.models.offer_data_models.Offer;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for Workship codebook data entity.
 * An instance represents row in workships table.
 */
@Entity
@Table(name = "workships")
public class Workship extends CodedEntity {

    @ManyToMany(mappedBy = "workships")
    private Set<Offer> offers = new HashSet<>();

    public Workship() {
    }

    public Workship(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
}