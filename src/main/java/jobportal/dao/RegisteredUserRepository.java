package jobportal.dao;

import jobportal.models.internal_models.data_entites.user.RegisteredUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RegisteredUserRepository extends PagingAndSortingRepository<RegisteredUser, Integer> {

    RegisteredUser findByEmail(String email);

    RegisteredUser findById(int id);

    Page<RegisteredUser> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    @Query(
            value = "SELECT ru.email FROM registered_users ru where ru.email LIKE %:term%",
            nativeQuery = true)
    List<String> findEmailsLikeSearchTerm(@Param("term") String term);

    @Query(
            value = "SELECT * FROM registered_users WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    Page<RegisteredUser> findRegisteredUsersByFirstNameLastNamePageable(String name, Pageable pageable);

    @Query(
            value = "SELECT CONCAT(first_name, ' ', last_name) FROM registered_users WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    List<String> findFullNamesLikeSearchTerm(String searchTerm);

    @Query(
            value = "SELECT * FROM registered_users WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%') AND email LIKE %?2%",
            nativeQuery = true)
    Page<RegisteredUser> findRegisteredUsersByFirstNameLastNameAndEmailPageable(String name, String email, Pageable pageable);

}