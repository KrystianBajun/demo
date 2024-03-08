package com.camundaexample.camunda2;

import java.util.Map;

public record SubmitFormDTO(
        Map<String, Object> values,
        long key
) {
}
