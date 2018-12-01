package bussiness_layer.services;

import bussiness_layer.dto.ProfessorDTO;
import bussiness_layer.dto.StudentDTO;
import bussiness_layer.dto.UserDTO;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username");
        }
        if(user instanceof Student){
            return new StudentDTO();
        }else{
            return new ProfessorDTO();
        }
    }
}
