package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends  AbstractMapService<TaskDTO,Long> implements TaskService {



    @Override
    public TaskDTO save(TaskDTO task) {
        if(task.getTaskStatus() == null)
            task.setTaskStatus(Status.OPEN);

        if(task.getAssignedData() == null)
            task.setAssignedData(LocalDate.now());

        if(task.getId()==null)
            task.setId(UUID.randomUUID().getMostSignificantBits());



        return super.save(task.getId(), task);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(TaskDTO task) {
          TaskDTO fondTask = findById(task.getId());
          task.setTaskStatus(fondTask.getTaskStatus());
          task.setAssignedData(fondTask.getAssignedData());

          super.update(task.getId(), task);
    }

    @Override
    public void deleteById(Long id) {
          super.deleteById(id);
    }

    @Override
    public List<TaskDTO> findTaskByManager(UserDTO manager) {
        return findAll().stream().filter(t->t.getProject().getAssignedManager().equals(manager)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {
        return findAll().stream().filter(t->!t.getTaskStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatus(Status status) {
        return findAll().stream().filter(t->t.getTaskStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO task) {
        findById(task.getId()).setTaskStatus(task.getTaskStatus());
        update(task);
    }
}
