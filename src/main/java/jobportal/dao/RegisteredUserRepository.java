package jobportal.dao;

import jobportal.models.internal_models.user.RegisteredUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RegisteredUserRepository extends CrudRepository <RegisteredUser, Integer>
{

    RegisteredUser findRegisteredUserByEmail(String email);

    @Query(
            value = "SELECT * FROM registered_users WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    List<RegisteredUser> findRegisteredUsersByFirstNameLastName(String name);

}