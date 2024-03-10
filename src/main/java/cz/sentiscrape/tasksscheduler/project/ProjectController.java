package cz.sentiscrape.tasksscheduler.project;

import cz.sentiscrape.tasksscheduler.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/projects/{id}")
    Project getProject(@PathVariable Integer id){
        try {
            return projectService.getProjectById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/projects")
    List<Project> getProjects(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted){
        return projectService.getAllProjects(isDeleted);
    }

    @PostMapping("/projects")
    Project addProject(@RequestBody ProjectDto project){
        return projectService.saveProject(project);
    }

    @PutMapping("/projects/{id}")
    ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody ProjectDto project){
        try {
            return ResponseEntity.ok(projectService.updateProject(id, project));
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/projects/{id}")
    void deleteProject(@PathVariable Integer id){
        projectService.deleteProject(id);
    }
}
