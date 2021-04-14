package jobportal.models.internal_models.codebooks;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model class for EduGeneralField codebook data entity.
 * An instance represents row in edu_general_fields table.
 */
@Entity
@Table(name = "edu_general_fields")
public class EduGeneralField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Choose ANN code")
    @Column(name = "ann_code")
    private int annCode;

    @Column(name = "name")
    @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters long")
    private String name;

    @Column(name = "pretty_name")
    @Size(min = 2, max = 500, message = "Pretty name must be between 2 and 500 characters long")
    private String prettyName;

    public EduGeneralField() {
    }

    public EduGeneralField(int id, @NotNull(message = "Choose ANN code") int annCode, @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters long") String name, @Size(min = 2, max = 500, message = "Pretty name must be between 2 and 500 characters long") String prettyName) {
        this.id = id;
        this.annCode = annCode;
        this.name = name;
        this.prettyName = prettyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnCode() {
        return annCode;
    }

    public void setAnnCode(int annCode) {
        this.annCode = annCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }
}