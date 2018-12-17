package web_layer.controllers;

import java.security.Principal;
import java.util.List;

import bussiness_layer.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.services.IProfessorService;
import web_layer.utils.ViewHelper;

@Controller
@RequestMapping("/professor")
public class ProfessorMvcController {

    @Autowired
    private IProfessorService service;

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
