package web_layer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public interface IUserMvcController {
    @GetMapping("/login")
    ModelAndView getLoginPage();

    @GetMapping("/")
    String getHomePage(Principal crtUser);
}
