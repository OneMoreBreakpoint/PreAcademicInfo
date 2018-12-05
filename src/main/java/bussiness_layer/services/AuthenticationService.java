package bussiness_layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import data_layer.domain.Professor;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.IUserRepository;

@Service
public class AuthenticationService implements AuthenticationManager, UserDetailsService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Optional<User> optionalUser = userRepo.findOneByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new BadCredentialsException("Invalid username");
        }

        User user = optionalUser.get();
        if (!BCrypt.checkpw(password, user.getEncryptedPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> userRoles = this.getUserRoles(user);
        userRoles.forEach(userRole -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(userRole));
        });
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findOneByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid username");
        }
        User user = optionalUser.get();
        org.springframework.security.core.userdetails.User.UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getEncryptedPassword());
        builder.roles(this.getUserRoles(user).get(0));
        return builder.build();
    }

    private List<String> getUserRoles(User user) {
        if (user instanceof Professor) {
            user = (Professor) user;
        } else if (user instanceof Student) {
            user = (Student) user;
        }
        List<String> userRoles = new ArrayList<>();
        String userRole = user.getClass().getSimpleName().toUpperCase();
        userRoles.add(userRole);
        return userRoles;
    }
}
