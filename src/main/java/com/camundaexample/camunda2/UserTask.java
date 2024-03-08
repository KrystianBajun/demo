package com.camundaexample.camunda2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_task")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_key")
    private Long key;

    @Column(name = "created")
    private OffsetDateTime created;

    public UserTask(Long key, OffsetDateTime created) {
        this.key = key;
        this.created = created;
    }
}