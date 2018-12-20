package web_layer.controllers;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.services.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web_layer.utils.ViewHelper;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorMvcController {

    @Autowired
    private IProfessorService service;

    public ProfessorMvcController(IProfessorService service) {
        this.service = service;
    }


    @GetMapping("/dashboard")
    public ModelAndView getDashboardPage(Principal crtUser) {
        List<ProfessorCourseDto> professorCourses = service.getRelatedCourses(crtUser.getName());
        return new ModelAndView("/professor/dashboard")
                .addObject("professorCourses", professorCourses)
                .addObject("viewHelper", ViewHelper.class);
    }

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(@RequestParam String course, @RequestParam String group, Principal crtUser) {
        List<EnrollmentDto> enrollments = service.getEnrollments(crtUser.getName(), course, group);
        List<ProfessorRightDto> professorRights = service.getProfessorRights(crtUser.getName(), course, group);
        List<GroupDto> groups = service.getGroups(crtUser.getName(), course);
        return new ModelAndView("/professor/timeline")
                .addObject("enrollments", enrollments)
                .addObject("rights", professorRights)
                .addObject("groups", groups)
                .addObject("crtGroupCode", group)
                .addObject("viewHelper", ViewHelper.class);
    }
}
