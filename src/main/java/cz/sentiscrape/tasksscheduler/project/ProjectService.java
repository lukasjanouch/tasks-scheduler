package cz.sentiscrape.tasksscheduler.project;

import cz.sentiscrape.tasksscheduler.exception.NotFoundException;
import jakarta.persistence.EntityManager;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private EntityManager entityManager;

    public Project saveProject(ProjectDto project) {
        Project newProject = Project.builder()
                .name(project.getName()).build();
        return projectRepository.save(newProject);
    }

    public List<Project> getAllProjects(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProjectFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Project> projects = projectRepository.findAll();
        session.disableFilter("deletedProjectFilter");
        return projects;
    }

    public Project getProjectById(Integer id) throws NotFoundException {
        return projectRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }

    public Project updateProject(Integer id, ProjectDto project) throws BadRequestException {
        Project existingProject = projectRepository.findById(id).orElseThrow(BadRequestException::new);
        existingProject.setName(project.getName());
        return projectRepository.save(existingProject);
    }
}
