package jobportal.models.internal_models.data_entites.user;

import javax.persistence.*;

/**
 * Model class for Administrator entity.
 * An instance represents row in administrators table.
 */

@Entity
@Table(name = "administrators")
public class Administrator extends Person {
}
