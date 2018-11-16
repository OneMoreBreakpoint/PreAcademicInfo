package web_layer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/professor")
public class ProfessorMvcController {

    @GetMapping("/dashboard")
    public ModelAndView getDashboardPage(){
        return new ModelAndView("/professor/dashboard");
    }

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(){
        return new ModelAndView("/professor/timeline");
    }
}
