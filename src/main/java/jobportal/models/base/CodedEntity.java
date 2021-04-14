package jobportal.models.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * Parent class/super class for all child classes with id, name and code attributes,
 * - mainly for MPSV data codebook entites e.g. Profession, Skill, Benefit, Village, District, Region ...
 * id, name and code attributes are inherited from this class.
 */
@MappedSuperclass
public class CodedEntity {

    @Id
    @Column(name = "id", length = 60)
    @Size(min = 1, max = 60, message = "ID must be between 1 and 60 characters long")
    private String id;

    @Column(name = "name")
    @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters long")
    private String name;

    @Column(name = "code")
    @Size(min = 1, max = 32, message = "Code must be between 1 and 32 characters long")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}