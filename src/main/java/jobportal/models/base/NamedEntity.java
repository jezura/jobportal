package jobportal.models.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * Parent class/super class for all child classes with id and name attributes.
 * id and name attributes are inherited from this class.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(name = "name")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
