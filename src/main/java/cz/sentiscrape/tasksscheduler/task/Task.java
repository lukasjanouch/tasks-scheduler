package cz.sentiscrape.tasksscheduler.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.sentiscrape.tasksscheduler.project.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Integer id;
    private String name;
    @ManyToOne
    @JsonIgnore
    private Project project;
    @Enumerated(EnumType.STRING)
    private State state;
}
