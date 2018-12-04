package web_layer.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.dto.StudentDTO;
import bussiness_layer.dto.UserDTO;
import bussiness_layer.services.IUserService;

@Controller
public class UserMvcController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        return new ModelAndView("login.html");
    }

    @GetMapping("/")
    public String getHomePage(Principal crtUser) {
        UserDTO user = userService.getUserByUsername(crtUser.getName());
        if (user instanceof StudentDTO) {
            return "redirect:/student/timeline";
        } else {
            return "redirect:/professor/dashboard";
        }
    }
}
