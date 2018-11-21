import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class App {


    public static void main(String[] args) throws MalformedURLException {

        //File f = new File("src");
      //  File fs = new File(f,"ApiDemos-debug.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.1.6");
        //capabilities.setCapability(MobileCapabilityType.APP, "com.wetek.wetv");
        capabilities.setCapability("appPackage", "com.wetek.wetv");
        capabilities.setCapability("appActivity", ".activities.MainActivity");
        AndroidDriver<AndroidElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);

    }
}
