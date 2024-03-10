package cz.sentiscrape.tasksscheduler.task;

import cz.sentiscrape.tasksscheduler.exception.ForbiddenException;
import cz.sentiscrape.tasksscheduler.exception.NotFoundException;
import cz.sentiscrape.tasksscheduler.project.Project;
import cz.sentiscrape.tasksscheduler.project.ProjectRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    public Task getTaskById(Integer id) throws NotFoundException {
        return taskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task saveTask(Integer projectId, TaskDto task) throws BadRequestException {
        Project tasksProject = projectRepository.findById(projectId).orElseThrow(BadRequestException::new);
        Task newTask = Task.builder()
                .name(task.getName())
                .state(State.STARTED)
                .project(tasksProject)
                .build();
        return taskRepository.save(newTask);
    }

    public Task updateTask(Integer id, TaskDto task) throws BadRequestException, ForbiddenException {
        Task existingTask = taskRepository.findById(id).orElseThrow(BadRequestException::new);
        if (existingTask.getState() == State.CLOSED) {
            throw new ForbiddenException();
        } else {
            existingTask.setName(task.getName());
            existingTask.setState(task.getState());
            return taskRepository.save(existingTask);
        }
    }

    public void deleteTask(Integer id) throws BadRequestException, ForbiddenException {
        Task existingTask = taskRepository.findById(id).orElseThrow(BadRequestException::new);
        if (existingTask.getState() == State.PROCESSING || existingTask.getState() == State.CLOSED) {
            throw new ForbiddenException();
        }else {
            taskRepository.deleteById(id);
        }
    }

    public List<Task> getTasksByProject(Integer projectId) throws BadRequestException {
        Project tasksProject = projectRepository.findById(projectId).orElseThrow(BadRequestException::new);
        return tasksProject.getTasks();
    }
}
