package jobportal.models.internal_models.codebooks;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model class for CzechName codebook data entity.
 * An instance represents row in czech_names table.
 */
@Entity
@Table(name = "czech_names")
public class CzechName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Choose first name")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Choose gender")
    @Column(name = "gender")
    private String gender;

    public CzechName() {
    }

    public CzechName(int id, @NotNull(message = "Choose first name") String name, @NotNull(message = "Choose gender") String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}