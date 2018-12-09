package web_layer.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.TeachingDto;
import bussiness_layer.services.IProfessorService;
import bussiness_layer.services.IStudentService;
import bussiness_layer.utils.Authorizer;

@Controller
@RequestMapping("/student")
public class StudentMvcController {
    @Autowired
    private IStudentService service;

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(Principal crtUser) {
        ModelAndView mv = new ModelAndView("/student/timeline");
        List<EnrollmentDto> enrollments = service.getEnrollments(crtUser.getName());
        enrollments.forEach(x-> System.out.println(x.getStudent().getUsername() +" "+
                x.getStudent().getFirstName()+" "+x.getCourse().getCode() +" "+x.getCourse().getName() +
                " "+x.getLessons().get(1).getId()));
        mv.addObject("enrollments", enrollments);
        mv.addObject("auth", Authorizer.class);
        return mv;
    }
}
