package bussiness_layer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bussiness_layer.dto.UserDTO;
import data_layer.domain.User;
import data_layer.mappers.UserMapper;
import data_layer.repositories.IUserRepository;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    @Transactional
    public UserDTO getUserByUsername(String username) {
        Optional<User> optionalUser = userRepo.findOneByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid username");
        } else {
            User user = optionalUser.get();
            return UserMapper.toUserDTO(user);

            //            if (user instanceof Student) {
//                return new StudentDTO();
//            } else {
//                return new ProfessorDTO();
//            }
        }
    }
}
