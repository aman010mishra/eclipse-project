package cse339ca1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class ClassFile {

    static AndroidDriver driver;
    static WebDriverWait wait;

    // Wait for emulator to report boot complete
    private void waitForEmulatorBoot(String deviceId) throws IOException, InterruptedException {
        String adbCommand = "adb";
        String[] command = {adbCommand, "-s", deviceId, "shell", "getprop", "sys.boot_completed"};
        int attempts = 60; // up to ~5 minutes
        while (attempts-- > 0) {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();
            process.destroy();
            if ("1".equals(output)) {
                System.out.println("Emulator booted successfully.");
                return;
            }
            Thread.sleep(5000);
        }
        throw new RuntimeException("Emulator did not boot within expected time.");
    }

    @BeforeClass
    public void setup() throws IOException, InterruptedException {
        // Start emulator
        ProcessBuilder pb = new ProcessBuilder(
                "cmd", "/c", "start", "cmd.exe", "/K", "emulator -avd Medium_Phone_API_36.0"
        );
        pb.start();

        String deviceId = "emulator-5554";      // make sure this matches `adb devices`
        waitForEmulatorBoot(deviceId);

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setUdid(deviceId);
        options.setDeviceName("Medium_Phone_API_36.0");

        // Point to your MyDemoAppRN apk
        options.setApp("C:\\Users\\Aman\\Downloads\\Android-MyDemoAppRN.1.3.0.build-244.apk");

        // Explicit package/activity for faster, more reliable launch
        options.setAppPackage("com.saucelabs.mydemoapp.rn");
        options.setAppActivity("com.saucelabs.mydemoapp.rn.MainActivity"); // main activity for this app[web:43][web:53]

        // extra caps
        options.setCapability("skipDeviceInitialization", true);
        options.setCapability("ignoreHiddenApiPolicyError", true);
        options.setCapability("uiautomator2ServerLaunchTimeout", 90000);
        options.setCapability("newCommandTimeout", 3600);

        driver = new AndroidDriver(URI.create("http://127.0.0.1:4723/").toURL(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("Driver session created, MyDemoAppRN launched.");
    }

    @Test
    public void dummyTest() {
        System.out.println("App is running!");
        Assert.assertNotNull(driver);
    }

    /**
     * Example locator test for MyDemoAppRN login screen.
     * Locators are examples based on common setups; inspect with Appium Inspector
     * and replace IDs/accessibilityIds accordingly. [web:43][web:48]
     */
    @Test
    public void testLoginLocators() {
        // Example: tap Login menu, enter username & password, tap login.
        // Replace these with real identifiers from the app:

        // Example using accessibility id (content-desc) for the menu LOGIN
        WebElement menuLogin = driver.findElement(By.accessibilityId("open menu"));
        menuLogin.click();

        WebElement menuItemLogin = driver.findElement(By.xpath("//*[@text='Login']"));
        menuItemLogin.click();

        // Example IDs – change after inspecting the app:
        WebElement usernameField =
                driver.findElement(By.xpath("//android.widget.EditText[@content-desc='Username input field']"));
        WebElement passwordField =
                driver.findElement(By.xpath("//android.widget.EditText[@content-desc='Password input field']"));
        WebElement loginButton =
                driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Login button']"));

        usernameField.clear();
        usernameField.sendKeys("bob@example.com");
        passwordField.clear();
        passwordField.sendKeys("10203040");
        loginButton.click();

        // Example check (e.g., home screen visible after login)
        // Replace with the correct locator for a post-login element:
        WebElement productsTitle =
                driver.findElement(By.xpath("//*[@text='Products']"));
        Assert.assertTrue(productsTitle.isDisplayed());
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
