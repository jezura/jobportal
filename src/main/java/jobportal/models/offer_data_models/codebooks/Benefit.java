package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

/**
 * Model class for Benefit codebook data entity.
 * An instance represents row in benefits table.
 */
@Entity
@Table(name = "benefits")
public class Benefit extends CodedEntity {
    @OneToMany(mappedBy = "benefit")
    private Set<OfferBenefit> offerBenefits = new HashSet<OfferBenefit>();

    public Benefit() {
    }

    public Set<OfferBenefit> getOfferBenefits() {
        return offerBenefits;
    }

    public void setOfferBenefits(Set<OfferBenefit> offerBenefits) {
        this.offerBenefits = offerBenefits;
    }

    public void addOfferBenefit(OfferBenefit offerBenefit) {
        this.offerBenefits.add(offerBenefit);
    }
}