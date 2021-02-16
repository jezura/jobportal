package jobportal.services;


import jobportal.models.internal_models.user.Administrator;
import jobportal.models.internal_models.user.RegisteredUser;
import jobportal.models.internal_models.security.MyUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional <RegisteredUser> optionalRegisteredUser = Optional.ofNullable(personService.findRegisteredUserByEmail(email));
        if(optionalRegisteredUser.isPresent()) {
            return new  MyUser(optionalRegisteredUser.get().getEmail(), optionalRegisteredUser.get().getPassword(), optionalRegisteredUser.get().getRole() ,optionalRegisteredUser.get().getId());
        }
        Optional <Administrator> optionalAdministrator = Optional.ofNullable(personService.findAdministratorByEmail(email));
        if(optionalAdministrator.isPresent()) {
            return new  MyUser(optionalAdministrator.get().getEmail(), optionalAdministrator.get().getPassword(), optionalAdministrator.get().getRole() ,optionalAdministrator.get().getId());
        }
        return null;
    }
}