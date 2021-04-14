package jobportal.models.internal_models.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * MyUser class implements UserDetails interface (Spring Security).
 * An instance of this class maintains, after the user logs in,
 * basic authentication and authorization information about this logged in user.
 */
public class MyUser implements UserDetails {

    private String userName;
    private String password;
    private String role;
    private int id;

    public MyUser(String userName, String password, String role, int id) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public MyUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        getRoleList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + p);
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
}
