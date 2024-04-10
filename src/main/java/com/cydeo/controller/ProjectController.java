package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {
  private final UserService userService;

  private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createProject(Model model){
        model.addAttribute("project",new ProjectDTO());
        model.addAttribute("managers",userService.findAll());
        model.addAttribute("projects",projectService.findAll());



       return "/project/create";
    }
    @PostMapping("/create")
    public String saveProject(@ModelAttribute("project") ProjectDTO project){

         projectService.save(project);
        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode){
        projectService.deleteById(projectCode);

        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectCode}")
    public String editUser(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("project",projectService.findById(projectCode));
        model.addAttribute("managers",userService.findAll());
        model.addAttribute("projects",projectService.findAll());

        return "/project/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("project") ProjectDTO project){

        projectService.update(project);
        return "redirect:/project/create";
    }

}
