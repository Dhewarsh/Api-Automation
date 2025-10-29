package com.api.automation.utils;

public class OctopusVariables {
    public static String getVariable(String variableName) {
        // First try to get from Octopus (will be injected during deployment)
        String value = System.getenv("Octopus_" + variableName);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        
        // Fallback to local config for local development
        // Try System properties first, then a config.properties on the classpath
        String local = System.getProperty(variableName);
        if (local != null && !local.isEmpty()) {
            return local;
        }
        try (java.io.InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                java.util.Properties props = new java.util.Properties();
                props.load(is);
                String prop = props.getProperty(variableName);
                if (prop != null && !prop.isEmpty()) {
                    return prop;
                }
            }
        } catch (java.io.IOException e) {
            // ignore and fall through to return null
        }
        return null;
    }
}
