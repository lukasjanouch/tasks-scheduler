package cz.sentiscrape.tasksscheduler.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.sentiscrape.tasksscheduler.task.Task;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.descriptor.java.BooleanJavaType;

import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE project SET deleted = true WHERE id=?")
@FilterDef(name = "deletedProjectFilter", parameters = @ParamDef(name = "isDeleted", type = BooleanJavaType.class))
@Filter(name = "deletedProjectFilter", condition = "deleted = :isDeleted")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Project {

    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Task> tasks;
    private boolean deleted = Boolean.FALSE;
}
