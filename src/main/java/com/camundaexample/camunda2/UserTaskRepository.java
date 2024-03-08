package com.camundaexample.camunda2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface UserTaskRepository extends JpaRepository<UserTask, Long> {

    Optional<UserTask> findByKey(long key);
    @Query(value = "SELECT * FROM user_task ORDER BY created DESC LIMIT 1", nativeQuery = true)
    Optional<UserTask> findLatestUserTask();

    Optional<UserTask> findFirstByOrderByCreatedDesc();
}
