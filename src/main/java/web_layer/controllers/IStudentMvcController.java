package web_layer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/student")
public interface IStudentMvcController {

    @GetMapping("/timeline")
    ModelAndView getTimelinePage();
}
