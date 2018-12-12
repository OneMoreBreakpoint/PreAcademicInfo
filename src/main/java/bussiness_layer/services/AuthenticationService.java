package bussiness_layer.services;

import data_layer.domain.User;
import data_layer.repositories.IUserRepository;
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
import utils.exceptions.ExceptionMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthenticationService implements AuthenticationManager, UserDetailsService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        User user = userRepo.findByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getEncryptedPassword())) {
            throw new BadCredentialsException(ExceptionMessages.INVALID_USERNAME_OR_PASSWORD);
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> userRoles = Arrays.asList(user.getUserRole());
        userRoles.forEach(userRole -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(userRole));
        });
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.INVALID_USERNAME);
        }
        org.springframework.security.core.userdetails.User.UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getEncryptedPassword());
        builder.roles(user.getUserRole());
        return builder.build();
    }
}
