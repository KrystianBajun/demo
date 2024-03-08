package com.camundaexample.camunda2;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CamundaDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamundaDemoApplication.class, args);
    }

    @JobWorker(type = "isAnotherUserTaskActive")
    public Map<String, Object> isAnotherUserTaskActive(@Variable String pesel) {
        System.out.println(pesel);
        HashMap<String, Object> variable = new HashMap<>();
        variable.put("shouldStartProcess", true);
        return variable;
    }

    @JobWorker(type = "getPolicy")
    public Map<String, Object> getPolicy(@Variable String policyNumber) {
        System.out.println("SELECT policy from db");
        Policy policy;
        if (policyNumber.equals("activePolicy")) {
            policy = new Policy("123", true, "/245", 20, false, "PERSONAL_AUTO", 0);
        } else {
            policy = new Policy("123", false, "/245", 20, true, "PERSONAL_AUTO", 1);
        }
        HashMap<String, Object> variable = new HashMap<>();
        variable.put("policy", policy);
        return variable;
    }
}
