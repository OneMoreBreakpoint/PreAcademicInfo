package bussiness_layer.services;

import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.dto.UserDto;
import data_layer.domain.Professor;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.IProffesorRepository;
import data_layer.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utils.exceptions.ExceptionMessages;

import javax.transaction.Transactional;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IProffesorRepository proffesorRepository;

    @Override
    @Transactional
    public UserDto getUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.INVALID_USERNAME);
        }
        if (user instanceof Student) {
            return new StudentDto();
        } else {
            return new ProfessorDto();
        }
    }

    @Override
    @Transactional
    public ProfessorDto getProffesorByUsername(String username) {
        Professor user = proffesorRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.INVALID_USERNAME);
        }
        System.out.println(user);
        return null;
//        if (user instanceof Student) {
//            return new StudentDto(username,password,firstName,lastName,email,registrationNr,fathersInitials,pathToProfilePhoto,notifiedByEmail,group);
//        } else {
//            return new ProfessorDto();
//        }
    }
}
