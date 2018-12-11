package web_layer.controllers;

import java.security.Principal;
import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;
import bussiness_layer.dto.TeachingDto;
import bussiness_layer.services.IProfessorService;
import bussiness_layer.services.IStudentService;
import bussiness_layer.utils.Authorizer;
import bussiness_layer.utils.TemplateLists;
import data_layer.domain.Lesson;
import data_layer.domain.PartialExam;

@Controller
@RequestMapping("/student")
public class StudentMvcController {
    @Autowired
    private IStudentService service;

    @GetMapping("/timeline")
    public ModelAndView getTimelinePage(Principal crtUser) {
        ModelAndView mv = new ModelAndView("/student/timeline");
        List<EnrollmentDto> enrollments = service.getEnrollments(crtUser.getName());
        SortedSet<LessonDto> lessonsSet = service.getLessonsTemplateSet(crtUser.getName());
        SortedSet<PartialExamDto> partialExamSortedSet = service.getExamsTemplateSet(crtUser.getName());

        mv.addObject("templateLists", TemplateLists.class);
        mv.addObject("enrollments", enrollments);
        mv.addObject("lessonsTemplateSet", lessonsSet);
        mv.addObject("partialExamTemplateSet", partialExamSortedSet);

        return mv;
    }
}
