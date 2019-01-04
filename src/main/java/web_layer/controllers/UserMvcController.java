package web_layer.controllers;


import bussiness_layer.dto.UserDto;
import bussiness_layer.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import utils.Constants;

import java.security.Principal;

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
        return Constants.REDIRECT + user.getRole().toLowerCase() + "/";
    }
}
