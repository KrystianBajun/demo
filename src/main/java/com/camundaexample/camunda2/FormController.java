package com.camundaexample.camunda2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class FormController {
    private final ProcessService processService;

    @PostMapping("/start-process/add-coowner")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<String> startProcessAddCoowner() {
        return processService.startProcess();
    }

    @PostMapping("/submit-form")
    void submitForm(@RequestBody  SubmitFormDTO formData) {
        processService.submitForm(formData);
    }

    @GetMapping("/latest-user-task")
    @ResponseStatus(HttpStatus.OK)
    Optional<UserTask> getUserTask() {
        return processService.getUserTask();
    }

}