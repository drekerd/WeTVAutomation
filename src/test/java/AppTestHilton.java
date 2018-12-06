import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

public class AppTestHilton {

    static AndroidDriver<AndroidElement> driver;

    @BeforeTest
    public static void before_All_Test() throws IOException, InterruptedException {
        String deviceName = "192.168.1.7";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "wh220_hilton");
        capabilities.setCapability("usid", "WH22015702E18D75");
        capabilities.setCapability("platformVersion", "7.1.2");
        capabilities.setCapability("appPackage", "com.hilton.launcher");
        capabilities.setCapability("appActivity", "com.hilton.launcher.ui.HomeActivity");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", 0);

        //checkHasTuner();

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("set caps");

    }

    @Test
    public static void simple_Scan_Return_True() throws InterruptedException, MalformedURLException {

        try {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='LIVE TV']")));

        } catch (NoSuchElementException e) {
            System.out.println("Tried to find live tv, but Failed due to time condition surpassed 1 min");

        }
        driver.findElement(By.xpath("//android.widget.TextView[@text='LIVE TV']")).click();
        



    }


}

