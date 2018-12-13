package bussiness_layer.services;

import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.UserDto;
import bussiness_layer.mappers.ProfessorMapper;
import bussiness_layer.mappers.StudentMapper;
import data_layer.domain.Professor;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.IProffesorRepository;
import data_layer.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utils.exceptions.ExceptionMessages;
import utils.exceptions.ResourceNotFoundException;

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
        User user = userRepo.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        if (user instanceof Student) {
            return StudentMapper.toDto((Student) user);
        } else {
            return ProfessorMapper.toDto((Professor) user);
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
