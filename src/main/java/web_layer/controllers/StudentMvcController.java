package web_layer.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.services.IEnrollmentService;
import web_layer.utils.ViewHelper;

@Controller
@RequestMapping("/student")
public class StudentMvcController {
    @Autowired
    private IEnrollmentService enrollmentService;

    @GetMapping("/")
    public String getIndexPage() {
        return "redirect:/student/timeline";
    }

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(Principal crtUser) {
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollments(crtUser.getName());
        return new ModelAndView("/student/timeline")
                .addObject("enrollments", enrollments)
                .addObject("viewHelper", ViewHelper.class);
    }

}
