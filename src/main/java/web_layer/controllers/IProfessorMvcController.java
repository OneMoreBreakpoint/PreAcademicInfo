package web_layer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequestMapping("/professor")
public interface IProfessorMvcController {

    @GetMapping("/dashboard")
    ModelAndView getDashboardPage();

    @GetMapping("/timeline")
    ModelAndView getTimelinePage(@RequestParam String course, @RequestParam Short group, Principal crtUser);
}
