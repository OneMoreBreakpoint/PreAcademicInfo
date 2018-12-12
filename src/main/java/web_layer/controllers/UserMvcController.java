package web_layer.controllers;


import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.dto.TeachingDto;
import bussiness_layer.dto.UserDto;
import bussiness_layer.services.IUserService;
import bussiness_layer.utils.Authorizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

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
        ModelAndView mv = new ModelAndView("/profile_settings");
        System.out.println(crtUser.getName());
        UserDto user = userService.getUserByUsername(crtUser.getName());
        System.out.println(user.getUsername());
        if (user instanceof StudentDto)
            mv.addObject("Type", "student");
        else
            mv.addObject("Type", "teacher");
        mv.addObject("User", user);
        return mv;
    }
}
