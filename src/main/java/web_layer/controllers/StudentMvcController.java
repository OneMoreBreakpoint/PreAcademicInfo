package web_layer.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentMvcController {

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage() {
        return new ModelAndView("/student/timeline");
    }
}
