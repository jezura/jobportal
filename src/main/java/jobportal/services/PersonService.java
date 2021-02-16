package jobportal.services;

import jobportal.dao.RegisteredUserRepository;
import jobportal.dao.AdministratorRepository;
import jobportal.models.internal_models.user.Administrator;
import jobportal.models.internal_models.user.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;

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

    public Collection<RegisteredUser> findAllRegisteredUsers(){
        List<RegisteredUser> registeredUsers = new ArrayList<RegisteredUser>();
        for (RegisteredUser registeredUser :registeredUserRepository.findAll())
        {
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }

    public Collection<RegisteredUser> findRegisteredUsersByFirstNameLastName(String name){
        List<RegisteredUser> registeredUsers = new ArrayList<RegisteredUser>();
        for (RegisteredUser registeredUser :registeredUserRepository.findRegisteredUsersByFirstNameLastName(name))
        {
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }


    public RegisteredUser findRegisteredUserByEmail(String email){
        RegisteredUser registeredUser = registeredUserRepository.findRegisteredUserByEmail(email);
        return registeredUser;
    }

    public Administrator findAdministratorByEmail(String email){
        Administrator administrator = administratorRepository.findAdministratorByEmail(email);
        return administrator;
    }

    public boolean isUnique(String email){
        boolean unique = true;

        if(findRegisteredUserByEmail(email) != null)
            unique = false;

        if(findAdministratorByEmail(email) != null)
            unique = false;

        return unique;
    }

    public void deleteRegisteredUser(int id){
        registeredUserRepository.deleteById(id);
    }

    public void saveRegisteredUser(RegisteredUser ru){
        registeredUserRepository.save(ru);
    }

    public RegisteredUser getRegisteredUser(int id){
        return registeredUserRepository.findById(id).get();
    }
}