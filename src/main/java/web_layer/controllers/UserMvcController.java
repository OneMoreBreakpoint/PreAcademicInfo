package web_layer.controllers;

import bussiness_layer.dto.StudentDTO;
import bussiness_layer.dto.UserDTO;
import bussiness_layer.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserMvcController implements IUserMvcController{

    @Autowired
    private IUserService userService;

    public ModelAndView getLoginPage(){
        return new ModelAndView("login.html");
    }

    public String getHomePage(Principal crtUser){
        UserDTO user = userService.getUserByUsername(crtUser.getName());
        if(user instanceof StudentDTO){
            return "redirect:/student/timeline";
        }else{
            return "redirect:/professor/dashboard";
        }
    }
}
