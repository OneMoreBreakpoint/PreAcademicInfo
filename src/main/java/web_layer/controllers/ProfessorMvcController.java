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
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorRightDto;
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
        return new ModelAndView("/professor/dashboard").addObject("courses", professorCourses);
    }

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(@RequestParam String course, @RequestParam String group, Principal crtUser) {
        ModelAndView mv = new ModelAndView("/professor/timeline");
        List<EnrollmentDto> enrollments = service.getEnrollments(crtUser.getName(), course, group);
        List<ProfessorRightDto> professorRights = service.getProfessorRights(crtUser.getName(), course, group);
        List<GroupDto> groups = service.getGroups(crtUser.getName(), course);
        mv.addObject("enrollments", enrollments);
        mv.addObject("rights", professorRights);
        mv.addObject("groups", groups);
        mv.addObject("crtGroupCode", group);
        mv.addObject("viewHelper", ViewHelper.class);
        return mv;
    }
}
