package jobportal.services;

import jobportal.dao.RegisteredUserRepository;
import jobportal.dao.AdministratorRepository;
import jobportal.models.internal_models.data_entites.user.Administrator;
import jobportal.models.internal_models.data_entites.user.RegisteredUser;
import jobportal.models.offer_data_models.Offer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class PersonService {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;
    @Autowired
    private AdministratorRepository administratorRepository;

    public Page<RegisteredUser> findAllRegisteredUsersPageable(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return registeredUserRepository.findAll(pageable);
    }

    public Collection<RegisteredUser> findAllRegisteredUsers(){
        List<RegisteredUser> registeredUsers = new ArrayList<RegisteredUser>();
        for (RegisteredUser registeredUser :registeredUserRepository.findAll())
        {
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }

    public Page<RegisteredUser> findRegisteredUsersByFirstNameLastNamePageable(String name, int pageNumber, int size){
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return registeredUserRepository.findRegisteredUsersByFirstNameLastNamePageable(name, pageable);
    }

    public Page<RegisteredUser> findRegisteredUsersByFirstNameLastNameAndEmailPageable(String name, String email, int pageNumber, int size){
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return registeredUserRepository.findRegisteredUsersByFirstNameLastNameAndEmailPageable(name, email, pageable);
    }

    public RegisteredUser findRegisteredUserByEmail(String email){
        RegisteredUser registeredUser = registeredUserRepository.findByEmail(email);
        return registeredUser;
    }

    public Page<RegisteredUser> findRegisteredUserByEmailLikePageable(String email, int pageNumber, int size){
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return registeredUserRepository.findByEmailContainingIgnoreCase(email, pageable);
    }

    public RegisteredUser findRegisteredUserById(int id){
        RegisteredUser registeredUser = registeredUserRepository.findById(id);
        return registeredUser;
    }

    public Administrator findAdministratorByEmail(String email){
        Administrator administrator = administratorRepository.findAdministratorByEmail(email);
        return administrator;
    }

    public List<String> findEmailsLikeSearchTerm(String term){
        List<String> emails = registeredUserRepository.findEmailsLikeSearchTerm(term);
        return emails;
    }

    public List<String> findFullNamesLikeSearchTerm(String term){
        List<String> fullNames = registeredUserRepository.findFullNamesLikeSearchTerm(term);
        return fullNames;
    }


    public boolean isUnique(String email){
        boolean unique = true;

        if(findRegisteredUserByEmail(email) != null)
            unique = false;

        if(findAdministratorByEmail(email) != null)
            unique = false;

        return unique;
    }

    public long getRegisteredUsersCount(){
        return registeredUserRepository.count();
    }

    public void deleteRegisteredUser(int id){
        registeredUserRepository.deleteById(id);
    }

    public void saveRegisteredUser(RegisteredUser ru){
        registeredUserRepository.save(ru);
    }
}