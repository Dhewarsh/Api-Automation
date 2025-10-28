package com.api.automation.utils;

public class OctopusVariables {
    public static String getVariable(String variableName) {
        // First try to get from Octopus (will be injected during deployment)
        String value = System.getenv("Octopus_" + variableName);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        
        // Fallback to local config for local development
        return ConfigProperties.getProperty(variableName);
    }
}
