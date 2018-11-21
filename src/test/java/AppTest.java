import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
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
import java.util.NoSuchElementException;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

public class AppTest {

    static AndroidDriver<AndroidElement> driver;
    static boolean hasTuner;

    private static boolean isElementPresent(String path) throws InterruptedException {
        System.out.println("chamout");

        System.out.println("entrou no for");
        try {
            System.out.println("entrou no try");
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));

            System.out.println("saiu do try");
        } catch (NoSuchElementException e) {
            System.out.println("Tried to find live tv, but Failed due to time condition surpassed");
            return false;
        }


        return true;

    }

    public static boolean waitHalfSecond() throws InterruptedException{
        Thread.sleep(500);
        return true;
    }

    public static String getAdbDevices() throws IOException, InterruptedException {
        Runtime sysConsole = Runtime.getRuntime();
        Process p;
        p = sysConsole.exec("adb devices");
        p.waitFor();
        BufferedReader returnConsole = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while((line = returnConsole.readLine()) != null){
            System.out.println(line);
        }
        return line;
    }

    public static StringBuilder setAdbCommand(String command, String description) throws IOException, InterruptedException {
        Runtime sysConsole = Runtime.getRuntime();
        Process p;
        p = sysConsole.exec(command);
        p.waitFor();
        BufferedReader returnConsole = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder line = new StringBuilder("");
        System.out.println("Attempting to "+ description);
        while((line.append(returnConsole.readLine()).append("\n")) != null){
            //System.out.println(description);

            if(returnConsole.readLine()==null){
                return line;
            }
        }

        return null;
    }

    public static boolean checkHasTuner() throws IOException, InterruptedException {
        StringBuilder commandReturn;

        commandReturn = setAdbCommand("adb root", "connect as root");
        System.out.println("Connected as root "+commandReturn);
        Thread.sleep(10000);
        commandReturn = setAdbCommand("adb shell dmesg | grep dvb", null);
        System.out.println("Command dmesg\n "+commandReturn);
        String asd;
        if(commandReturn.toString().contains("AVL DVB-T/T2/C")){
            return hasTuner = true;
        }
        else{
            return hasTuner = false;
        }

    }


    @BeforeTest
    public static void before_All_Test() throws IOException, InterruptedException {
        String deviceName = "192.168.1.6";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.1.6");
        //capabilities.setCapability(MobileCapabilityType.APP, "com.wetek.wetv");
        capabilities.setCapability("appPackage", "com.wetek.wetv");
        capabilities.setCapability("appActivity", ".activities.MainActivity");

        boolean hasTuner = checkHasTuner();
        System.out.println(hasTuner);

        //driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @Test
    public static void simple_Scan_Return_True() throws InterruptedException, MalformedURLException {



        if(hasTuner){


            //driver.pressKeyCode(66);
            String checkLoadding = "";
            boolean loadStatus;
            int countLoading = 0;
            boolean checkIsElementPresent = isElementPresent("//android.widget.TextView[@text = 'Live TV']");
            if (checkIsElementPresent) {
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                Assert.assertTrue(checkIsElementPresent);
            } else {
                Assert.assertTrue(checkIsElementPresent);
            }
        }
        else
        {
            Assert.assertTrue(hasTuner);
            Assert.fail("Has Tuner = " + hasTuner);


        }

//


//
//
//        boolean scanDone;
//        do {
//            Thread.sleep(60000);
//            String scanStatus = driver.findElementByAccessibilityId("com.wetek.wetv:id/guidance_title").getText();
//            System.out.println(scanStatus);
//            if(scanStatus.contains("done"))
//            {
//                scanDone=true;
//                System.out.println("Scan Finished");
//            }
//            else
//            {
//                scanDone=false;
//                System.out.println("Scan Not Finished");
//            }
//
//        } while (!scanDone);
//


    }


}

