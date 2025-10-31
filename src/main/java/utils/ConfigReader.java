package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    public static Properties getConfig() {
        if (properties == null) {
            loadConfig();
        }
        return properties;}
    public static String get(String key) {
        if (properties == null) {
            loadConfig();        }
        return properties.getProperty(key);}
    private static void loadConfig() {
        properties = new Properties();
        try {
            String path = System.getProperty("user.dir") + "/config/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
            System.out.println("Loaded config from: " + path);} 
        catch (IOException e) {
            System.err.println("Could not load config.properties file");
            e.printStackTrace();
        }
    }
}
