package jobportal.models.offer_data_models.codebooks;


import jobportal.models.offer_data_models.Offer;

import javax.persistence.*;

@Entity
@Table(name = "offer_skill")
public class OfferSkill {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Column(name = "description")
    private String description;

    public OfferSkill() {}

    public OfferSkill(int id, Offer offer, Skill skill, String description) {
        this.id = id;
        this.offer = offer;
        this.skill = skill;
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

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}