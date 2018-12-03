package web_layer.controllers;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.TeachingDTO;
import bussiness_layer.services.IProfessorService;
import bussiness_layer.utils.Authorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorMvcController {

    @Autowired
    private IProfessorService service;

    @GetMapping("/dashboard")
    public ModelAndView getDashboardPage() {
        return new ModelAndView("/professor/dashboard");
    }

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(@RequestParam String course, @RequestParam Short group, Principal crtUser) {
        ModelAndView mv = new ModelAndView("/professor/timeline");
        List<EnrollmentDTO> enrollments = service.getEnrollmentsForProfessorByCourseAndGroup(crtUser.getName(), course, group);
        TeachingDTO teaching = service.getTeachingByProfessorAndCourse(crtUser.getName(), course);
        mv.addObject("enrollments", enrollments);
        mv.addObject("teaching", teaching);
        mv.addObject("auth", Authorizer.class);
        mv.addObject("crtGroupCode", group);
        return mv;
    }
}
