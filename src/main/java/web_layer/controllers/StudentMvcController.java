package web_layer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentMvcController implements IStudentMvcController {

    public ModelAndView getTimelinePage(){
        return new ModelAndView("/student/timeline");
    }
}
