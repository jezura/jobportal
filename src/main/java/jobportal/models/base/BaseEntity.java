package jobportal.models.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Parent class/super class for all child classes with id attribute.
 * Id attribute is inherited from this class.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}