package cz.sentiscrape.tasksscheduler.task;

import cz.sentiscrape.tasksscheduler.project.Project;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
    private String name;
    private Project project;
    @Enumerated(EnumType.STRING)
    private State state;
}
