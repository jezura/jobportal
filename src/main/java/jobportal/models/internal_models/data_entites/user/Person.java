package jobportal.models.internal_models.data_entites.user;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Parent class/super class for child classes Administrator and RegisteredUser.
 * Common properties are inherited from this class.
 */
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    @Pattern(regexp = "^[a-zA-ZáéíóúůýčšžřÁÉÍÓÚŮÝČŠŽŘ'´]{3,20}", message = "Neplatné křestní jméno, povolená délka 3-20 znaků")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[a-zA-ZáéíóúůýčšžřÁÉÍÓÚŮÝČŠŽŘ'´]{3,20}", message = "Neplatné příjmení. Povolená délka 3-20 znaků")
    private String lastName;

    @Column(name = "email")
    @Pattern(regexp = "^[a-zA-Z0-9.\\-_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}", message = "Neplatný email/login. Zadejte prosím email ve validním formátu")
    private String email;

    @Size(min = 5, message = "Heslo musí obsahovat alespoň 5 znaků")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}