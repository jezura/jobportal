package jobportal.models.offer_data_models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employers")
public class Employer {

    @Id
    @NotNull
    @Column(name = "ico")
    private int ico;

    @NotNull(message = "Choose employer name/company name")
    @Column(name = "name")
    private String name;

    public Employer() {
    }

    public Employer(@NotNull int ico, @NotNull(message = "Choose employer name/company name") String name) {
        this.ico = ico;
        this.name = name;
    }

    public int getIco() {
        return ico;
    }

    public void setIco(int ico) {
        this.ico = ico;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}