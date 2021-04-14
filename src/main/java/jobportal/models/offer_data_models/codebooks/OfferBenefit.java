package jobportal.models.offer_data_models.codebooks;

import jobportal.models.offer_data_models.Offer;
import javax.persistence.*;

/**
 * Model class for OfferBenefit codebook data entity.
 * M:N relationship multiplicity linking/join table.
 * An instance represents row in offer_benefit table.
 */
@Entity
@Table(name = "offer_benefit")
public class OfferBenefit {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "benefit_id")
    private Benefit benefit;

    @Column(name = "description")
    private String description;

    public OfferBenefit() {}

    public OfferBenefit(int id, Offer offer, Benefit benefit, String description) {
        this.id = id;
        this.offer = offer;
        this.benefit = benefit;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}