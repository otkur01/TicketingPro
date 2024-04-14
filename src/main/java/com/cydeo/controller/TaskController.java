package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/task")
public class TaskController {
     private TaskService taskService;
     private UserService userService;
     private ProjectService projectService;


    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createTask(Model model ){
        model.addAttribute("task",new TaskDTO());
        model.addAttribute("tasks",taskService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());

        return "task/create";
    }
    @PostMapping("/create")
    public String insertTask(@ModelAttribute("task") TaskDTO task) {
        taskService.save(task);

        return "redirect:/task/create";

    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id")Long id, Model model){
        taskService.deleteById(id);
        return "redirect:/task/create";
    }

    @GetMapping("/update/{id}")
    public String editTask(@PathVariable("id") Long id, Model model){
        model.addAttribute("task",taskService.findById(id));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());
        model.addAttribute("tasks",taskService.findAll());
      return "/task/update";
    }

//    @PostMapping("/update/{id}")
//     public String updateTask(@ModelAttribute("id")Long id ,TaskDTO task) {
//        task.setId(id);
//        taskService.update(task);
//
//        return "redirect:/task/create";
//     }

    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task) {
        taskService.update(task);

        return "redirect:/task/create";
    }

     @GetMapping("/employee/pending-tasks")
    public String employeePendingTask( Model model){
      model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETE));

        return "/task/pending-tasks";
     }
    @GetMapping("/employee/edit/{id}")
    public String pendingTask(@PathVariable("id") Long id, Model model){
        model.addAttribute("task",taskService.findById(id));
//        model.addAttribute("employees",userService.findEmployees());
//        model.addAttribute("projects", projectService.findAll());

        model.addAttribute("statuses", Status.values());
        model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETE));
        return "/task/status-update";
    }

    @PostMapping("/employee/update/{id}")
    public String employeeUpdateTask(TaskDTO task){
      taskService.updateStatus(task);


        return "redirect:/task/employee/pending-tasks";
    }



    @GetMapping("/employee/archive")
    public String employeeArchive ( Model model){
        model.addAttribute("tasks", taskService.findAllTasksByStatus(Status.COMPLETE));

        return "/task/archive";
    }


}
