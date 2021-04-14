package jobportal.models.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Parent class/super class for all child classes with Long id attribute.
 * Long id attribute is inherited from this class.
 */
@MappedSuperclass
public class LongIdEntity implements Serializable {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }
}