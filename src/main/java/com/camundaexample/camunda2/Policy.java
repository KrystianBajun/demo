package com.camundaexample.camunda2;

public record Policy(
        String policyNumber,
        boolean isActive,
        String riskStatisticalSymbol,
        Integer daysSinceInsuranceStart,
        boolean coownerLimit,
        String policyType,
        Integer activeLPolicyLiquidationProcessesCount
) {
}
