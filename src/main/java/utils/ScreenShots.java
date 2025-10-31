package utils;

import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenShots {

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dest = "reports/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            File destination = new File(dest);
            destination.getParentFile().mkdirs();
            Files.copy(src.toPath(), destination.toPath());
            return dest;
        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return null;
        }
    }
}
