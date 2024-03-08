package com.camundaexample.camunda2;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
@Component
public class ProcessService {
    private static final String ADD_COOWNER_PROCESS = "AddCoownerProcess";
    private static final String USER_TASK_JOB_WORKER_TYPE = "io.camunda.zeebe:userTask";

    private final UserTaskRepository userTaskRepository;
    private final ZeebeClient zeebeClient;
    private CompletableFuture<String> jobKeyFuture;

    CompletableFuture<String> startProcess() {
        jobKeyFuture = new CompletableFuture<>();
        ProcessInstanceEvent processInstance =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId("testProcess")
                        .latestVersion()
                        .send()
                        .join();
        System.out.println(processInstance.getProcessInstanceKey());
        return jobKeyFuture;
    }

    @JobWorker(type = USER_TASK_JOB_WORKER_TYPE, autoComplete = false, timeout = Long.MAX_VALUE / 2)
   public void handle(ActivatedJob job) {
        UserTask userTask = new UserTask(job.getKey(), OffsetDateTime.now());
        System.out.println("Started user task with key: " + job.getKey());
        jobKeyFuture.complete(String.valueOf(job.getKey()));
        userTaskRepository.save(userTask);
    }

    Optional<UserTask> getUserTask() {
        return userTaskRepository.findLatestUserTask();
    }

    void submitForm(SubmitFormDTO formData) {
        zeebeClient.newCompleteCommand(formData.key())
                .variables(formData.values())
                .send()
                .join();

        log.debug("Completed task of key {}", formData.key());
    }
}
