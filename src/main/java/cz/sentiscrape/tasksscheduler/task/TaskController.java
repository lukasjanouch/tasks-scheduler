package cz.sentiscrape.tasksscheduler.task;

import cz.sentiscrape.tasksscheduler.exception.ForbiddenException;
import cz.sentiscrape.tasksscheduler.exception.NotFoundException;
import cz.sentiscrape.tasksscheduler.project.Project;
import cz.sentiscrape.tasksscheduler.project.ProjectDto;
import cz.sentiscrape.tasksscheduler.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable Integer id){
        try {
            return taskService.getTaskById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tasks")
    List<Task> getTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("projects/{projectId}/tasks")
    List<Task> getTasksByProject(@PathVariable Integer projectId){
        try {
            return taskService.getTasksByProject(projectId);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/projects/{projectId}/tasks")
    ResponseEntity<Task> addTask(@PathVariable Integer projectId, @RequestBody TaskDto task){
        try {
            return ResponseEntity.ok(taskService.saveTask(projectId , task));
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody TaskDto task){
        try {
            return ResponseEntity.ok(taskService.updateTask(id, task));
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ForbiddenException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable Integer id){
        try {
            taskService.deleteTask(id);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ForbiddenException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.noContent().build();
    }
}
