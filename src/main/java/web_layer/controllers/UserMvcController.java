package web_layer.controllers;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.dto.UserDto;
import bussiness_layer.services.IUserService;

@Controller
public class UserMvcController {

    @Autowired
    private final IUserService userService;

    public UserMvcController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        return new ModelAndView("login.html");
    }

    @GetMapping("/")
    public String getHomePage(Principal crtUser) {
        UserDto user = userService.getUserByUsername(crtUser.getName());
        if (user instanceof StudentDto) {
            return "redirect:/student/timeline";
        } else {
            return "redirect:/professor/dashboard";
        }
    }

    @GetMapping("/profile_settings")
    public ModelAndView getProfileSettingsPage(Principal crtUser) {
        System.out.println(crtUser.getName());
        ModelAndView mv = new ModelAndView("/profile_settings");
        UserDto user = userService.getUserByUsername(crtUser.getName());
//        if (user instanceof StudentDto)
//        {
//            StudentDto studentDto = (StudentDto) userService.getUserByUsername(crtUser.getName());
//            System.out.println(studentDto.getUsername());
//            mv.addObject("User", studentDto);
//        }
//        else
//        {
        ProfessorDto professorDto = userService.getProffesorByUsername(crtUser.getName());
//        }

//        if (user instanceof StudentDto)
//            mv.addObject("Type", "student");
//        else
//            mv.addObject("Type", "teacher");
        return mv;
    }
}
