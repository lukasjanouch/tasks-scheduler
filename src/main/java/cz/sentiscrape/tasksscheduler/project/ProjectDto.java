package cz.sentiscrape.tasksscheduler.project;

import cz.sentiscrape.tasksscheduler.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDto {
    private String name;
    private List<Task> tasks;
}
