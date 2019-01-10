package bussiness_layer.services.impl;

import bussiness_layer.dto.UserDto;
import bussiness_layer.mappers.ProfessorMapper;
import bussiness_layer.mappers.StudentMapper;
import bussiness_layer.services.IUserService;
import data_layer.domain.Professor;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.IUserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        if (user instanceof Student) {
            return StudentMapper.toDto((Student) user);
        } else {
            return ProfessorMapper.toDto((Professor) user);
        }
    }

    @Override
    public void updatePassword(String username, String crtPassword, String newPassword) {
        User user = userRepo.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        if (!BCrypt.checkpw(crtPassword, user.getEncryptedPassword())) {
            throw new AccessForbiddenException();
        }
        user.setEncryptedPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userRepo.flush();
    }
}
