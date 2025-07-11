package com.fuelup.azs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    private static Map<String, Object> config;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("application.yml");
            config = mapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки конфигурации", e);
        }
    }

    public static String getDatabaseUrl() {
        return getNestedValue("database.url");
    }

    public static String getDatabaseUsername() {
        return getNestedValue("database.username");
    }

    public static String getDatabasePassword() {
        return getNestedValue("database.password");
    }

    public static String getDatabaseDriver() {
        return getNestedValue("database.driver");
    }

    public static String getHibernateDialect() {
        return getNestedValue("hibernate.dialect");
    }

    public static String getHibernateHbm2ddlAuto() {
        return getNestedValue("hibernate.hbm2ddl.auto");
    }

    public static Boolean getHibernateShowSql() {
        return Boolean.valueOf(getNestedValue("hibernate.show_sql"));
    }

    public static Boolean getHibernateFormatSql() {
        return Boolean.valueOf(getNestedValue("hibernate.format_sql"));
    }

    public static String getApiBaseUrl() {
        return getNestedValue("api.fuelup.base_url");
    }

    public static String getApiKey() {
        return getNestedValue("api.fuelup.api_key");
    }

    @SuppressWarnings("unchecked")
    private static String getNestedValue(String path) {
        String[] keys = path.split("\\.");
        Map<String, Object> current = config;

        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
            if (current == null) {
                return null;
            }
        }

        Object value = current.get(keys[keys.length - 1]);
        return value != null ? value.toString() : null;
    }
}