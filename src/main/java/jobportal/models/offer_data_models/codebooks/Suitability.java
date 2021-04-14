package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;
import jobportal.models.offer_data_models.Offer;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for Suitability codebook data entity.
 * An instance represents row in suitabilities table.
 */
@Entity
@Table(name = "suitabilities")
public class Suitability extends CodedEntity {

    @ManyToMany(mappedBy = "suitabilities")
    private Set<Offer> offers = new HashSet<>();

    public Suitability() {
    }

    public Suitability(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
}