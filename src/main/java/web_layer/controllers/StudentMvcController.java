package web_layer.controllers;

import java.security.Principal;
import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;
import bussiness_layer.services.IStudentService;
import bussiness_layer.utils.TemplateList;

@Controller
@RequestMapping("/student")
public class StudentMvcController {
    @Autowired
    private IStudentService service;

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(Principal crtUser) {
        ModelAndView mv = new ModelAndView("/student/timeline");
        List<EnrollmentDto> enrollments = service.getEnrollments(crtUser.getName());
        SortedSet<LessonDto> lessonsSortedSet = service.getLessonsTemplateSet(crtUser.getName());
        SortedSet<PartialExamDto> partialExamsSortedSet = service.getExamsTemplateSet(crtUser.getName());

        mv.addObject("templateList", TemplateList.class);
        mv.addObject("enrollments", enrollments);
        mv.addObject("lessonsTemplateSet", lessonsSortedSet);
        mv.addObject("partialExamsTemplateSet", partialExamsSortedSet);

        return mv;
    }
}
