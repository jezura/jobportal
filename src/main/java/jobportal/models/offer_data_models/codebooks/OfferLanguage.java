package jobportal.models.offer_data_models.codebooks;

import jobportal.models.offer_data_models.Offer;

import javax.persistence.*;

@Entity
public class OfferLanguage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "level")
    private String level;

    @Column(name = "description")
    private String description;

    public OfferLanguage() {}

    public OfferLanguage(int id, Offer offer, Language language, String level, String description) {
        this.id = id;
        this.offer = offer;
        this.language = language;
        this.level = level;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}