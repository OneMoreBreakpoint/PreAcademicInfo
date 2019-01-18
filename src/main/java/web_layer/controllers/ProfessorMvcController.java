package web_layer.controllers;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.exportToXML.ApachePOIExcelWrite;
import bussiness_layer.services.IEnrollmentService;
import bussiness_layer.services.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final IProfessorService professorService;

    @Autowired
    private final IEnrollmentService enrollmentService;

    private String courseExp;
    private String groupExp;

    public ProfessorMvcController(IProfessorService professorService, IEnrollmentService enrollmentService) {
        this.professorService = professorService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "redirect:/professor/dashboard";
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashboardPage(Principal crtUser) {
        List<ProfessorCourseDto> professorCourses = professorService.getRelatedCourses(crtUser.getName());
        return new ModelAndView("professor/dashboard")
                .addObject("professorCourses", professorCourses)
                .addObject("viewHelper", ViewHelper.class);
    }

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(@RequestParam String course, @RequestParam String group, Principal crtUser) {
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollments(crtUser.getName(), course, group);
        this.courseExp=course;
        this.groupExp=group;
        List<ProfessorRightDto> professorRights = professorService.getProfessorRights(crtUser.getName(), course, group);
        List<GroupDto> groups = professorService.getGroups(crtUser.getName(), course);
        return new ModelAndView("professor/timeline")
                .addObject("enrollments", enrollments)
                .addObject("rights", professorRights)
                .addObject("groups", groups)
                .addObject("crtGroupCode", group)
                .addObject("viewHelper", ViewHelper.class);
    }

    @GetMapping("/export")
    public ResponseEntity<?> doExport(@RequestParam  Boolean publicTipe, Principal crtUser) {
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollments(crtUser.getName(), this.courseExp, this.groupExp);

        ApachePOIExcelWrite apachePOIExcelWrite=new ApachePOIExcelWrite();
        apachePOIExcelWrite.exportData(enrollments,publicTipe);

        return ResponseEntity.ok(null);
    }
}
