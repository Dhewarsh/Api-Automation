package com.api.automation.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class EnvironmentConfig {
    private static final Properties props = new Properties();
    private static EnvironmentConfig instance;

    private EnvironmentConfig() {
        try {
            // Load environment-specific properties
            String env = System.getProperty("env", "Dev");
            String propFile = "config/" + env.toLowerCase() + ".properties";
            props.load(new FileInputStream(propFile));
        } catch (Exception e) {
            System.err.println("Error loading environment configuration: " + e.getMessage());
        }
    }

    public static EnvironmentConfig getInstance() {
        if (instance == null) {
            instance = new EnvironmentConfig();
        }
        return instance;
    }

    public String getBaseUrl() {
        return System.getProperty("base.url", ConfigProperties.BASE_URL);
    }

    public String[] getEmailRecipients() {
        String recipients = System.getProperty("emailRecipients", "");
        return recipients.split(",");
    }

    public String getApiKey() {
        return System.getProperty("api.key", ConfigProperties.API_KEY);
    }
}
