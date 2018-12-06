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
import java.net.URL;
import java.util.NoSuchElementException;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

public class AppTest {

    static AndroidDriver<AndroidElement> driver;
    static boolean hasTuner = true;

    private static boolean isElementPresent(String path) throws InterruptedException {


        System.out.println("Waiting for WeTV...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));

        } catch (NoSuchElementException e) {
            System.out.println("Tried to find live tv, but Failed due to time condition surpassed");
            return false;
        }


        return true;

    }

    public static boolean waitHalfSecond() throws InterruptedException {
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
        while ((line = returnConsole.readLine()) != null) {
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
        while ((line.append(returnConsole.readLine()).append("\n")) != null) {
            //System.out.println(description);

            if (returnConsole.readLine() == null) {
                return line;
            }
        }

        return null;
    }

    public static boolean checkHasTuner() throws IOException, InterruptedException {
        StringBuilder commandReturn;

        commandReturn = setAdbCommand("adb root", "connect as root");
        System.out.println("Connected as root " + commandReturn);
        Thread.sleep(10000);
        commandReturn = setAdbCommand("adb shell dmesg | grep dvb", null);
        //System.out.println("Command dmesg\n "+commandReturn);
        String asd;

        if (commandReturn.toString().contains("AVL DVB-T/T2/C")) {
            hasTuner = true;
            System.out.println("Has Tuner: " + hasTuner);
            return hasTuner;
        } else {
            hasTuner = false;
            System.out.println("Has Tuner: " + hasTuner);
            return hasTuner;
        }

    }

    public static boolean scanPercentage(String percentage) {

        if (percentage.equals("Scanning...10%")
                ||percentage.equals("Scanning...11%")
                ||percentage.equals("Scanning...11%")) {
            System.out.println("entrou no scanning maior que 10%");
            return true;
        }
        return false;
    }


    @BeforeTest
    public static void before_All_Test() throws IOException, InterruptedException {
        String deviceName = "192.168.1.7";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.1.7");
        //capabilities.setCapability(MobileCapabilityType.APP, "app-wetek-leanback-tvheadend-release.apk");
        capabilities.setCapability(MobileCapabilityType.APP, "com.wetek.wetv");
        capabilities.setCapability("appPackage", "com.wetek.wetv");
        capabilities.setCapability("appActivity", ".activities.MainActivity");
        capabilities.setCapability("newCommandTimeout", 0);

        //checkHasTuner();

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("set caps");

    }

    @Test
    public static void simple_Scan_Return_True() throws InterruptedException, MalformedURLException {
        /* HasTuner should be executed by a method
        that method was commented and this variable was
        implicit changed this
        value to true on delcarion above*/
        System.out.println("enterd Test");
        if (hasTuner) {
            //driver.pressKeyCode(66);
            String checkLoadding = "";
            boolean loadStatus, displayChannels = false;
            int countLoading = 0;
            boolean thisIsTrue =true, isThisFalse=false;

            System.out.println("checking if element is present");
            boolean checkIsElementPresent = isElementPresent("//android.widget.TextView[@text = 'Live TV']");
            System.out.println("result "+checkIsElementPresent);
            if (checkIsElementPresent) {

                waitHalfSecond();
//                driver.findElementByXPath("//android.widget.TextView[@text = 'Live TV']").click(); // Select Live TV
                driver.pressKey(new KeyEvent(AndroidKey.ENTER)); // Select LiveTV
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER)); // Select Simple Scan
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN)); // Choose Cable
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER)); //Select Cable
                waitHalfSecond();
                driver.pressKey(new KeyEvent(AndroidKey.ENTER)); //Select World
//                //Assert.assertTrue(checkIsElementPresent);
//                String scanUpdate;
//                String scanPast = "";
//                String channelsFound = "No Channels found", channelsUpdate = "";
                boolean isScanning = true;
                do {
                    System.out.println("Scanning Sarted");
                    Thread.sleep(10000);
//                    try {
//                        WebDriverWait wait = new WebDriverWait(driver, 60);
//                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='All done']")));
//                        isScanning=false;
//                    }catch (NoSuchElementException e){
//                        isScanning = true;
//                    }



//                    scanUpdate = driver.findElementByXPath("//android.widget.TextView[@index=1]").getText();
//                    if(scanPercentage(scanUpdate)){
//                        displayChannels = true;
//                    } //If scanning started, it will check if percentage is above 10% to avoid crash
//
//                    if (scanUpdate.equals(scanPast)) {
//                        Thread.sleep(1000);
//                    } else {
////                        System.out.println("ScanUpdate na verificacao: "+scanUpdate);
////                        System.out.println("ScanPast na verificacao: "+scanPast);
//
//
//                        if (scanUpdate.contains("done")) {
//                            isScanning=false;
//                            break;
//                        } else {
//
//                            //channelsFound = driver.findElementByXPath("//android.widget.TextView[@resource-id='com.wetek.wetv:id/guidedactions_item_title']").getText();
//                            if (displayChannels){
//                                System.out.println(scanUpdate + " " + channelsFound);
//                            }
//                            else
//                            {
//                                System.out.println(scanUpdate);
//                            }
//                            scanPast = scanUpdate;
//                            isScanning = true;
//                        }
//                    }




                } while (isScanning);
                System.out.println("Scan Completed");

                driver.findElementByXPath("//android.widget.LinearLayout[@index=0]").click();
                Thread.sleep(1000);
                String nrChannelsFound = driver.findElementByXPath("//android.widget.TextView[@index=1]").getText();
                System.out.println(nrChannelsFound);

//                channelsFound.equals(channelsUpdate);
//                if(channelsFound.contains("No")){
//                    Assert.fail("Test Result: "+channelsFound);
//                }
//                else{
//                    System.out.println("Test Result: "+ channelsFound);
//                    Assert.assertTrue(isScanning);
//                }


            } else {
                Assert.assertTrue(checkIsElementPresent);
            }
        } else {
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

