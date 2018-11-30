package web_layer.controllers;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.GroupDTO;
import bussiness_layer.services.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.SortedSet;

@Controller
public class ProfessorMvcController implements IProfessorMvcController {

    @Autowired
    private IProfessorService service;

    public ModelAndView getDashboardPage(){
        return new ModelAndView("/professor/dashboard");
    }

    public ModelAndView getTimelinePage(@RequestParam String course, @RequestParam Short group, Principal crtUser){
        ModelAndView mv = new ModelAndView("/professor/timeline");
        List<EnrollmentDTO> enrollments = service.getEnrollmentsByCourseAndGroup(course, group);
        SortedSet<GroupDTO> groups = service.getGroupsByProfessorAndCourse(crtUser.getName(), course);
        System.out.println(enrollments.get(0).getLaboratoryAttendance());
        mv.addObject("enrollments", enrollments);
        mv.addObject("groups", groups);
        mv.addObject("crtGroupCode", group);
        return mv;
    }
}
