package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for Language codebook data entity.
 * An instance represents row in languages table.
 */
@Entity
@Table(name = "languages")
public class Language extends CodedEntity {
    @OneToMany(mappedBy = "language")
    private Set<OfferLanguage> offerLanguages = new HashSet<OfferLanguage>();

    public Language() {
    }

    public Language(Set<OfferLanguage> offerLanguages) {
        this.offerLanguages = offerLanguages;
    }

    public Set<OfferLanguage> getOfferLanguages() {
        return offerLanguages;
    }

    public void setOfferLanguages(Set<OfferLanguage> offerLanguages) {
        this.offerLanguages = offerLanguages;
    }

    public void addOfferLanguage(OfferLanguage offerLanguage) {
        this.offerLanguages.add(offerLanguage);
    }
}