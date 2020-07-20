import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.testproject.sdk.drivers.ios.IOSDriver;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * iOS Test Basic Example.
 */
public final class recordiOS {

    /**
     * Default implicit timeout.
     */
    public static final int TIMEOUT = 5;

    /**
     * Main executable method.
     * @param args N/A
     * @throws Exception is thrown when driver initialization fails.
     */
    public static void main(final String[] args) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.UDID, "00008020-00124CD611F1002E");

        // Compile and deploy the App from source https://github.com/testproject-io/ios-demo-app
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "io.testproject.Demo");

        IOSDriver<MobileElement> driver = new IOSDriver<>("xMOUq-CTwc12Ru7Vjls1syE6uZ8B7vxcv1KAs55XaOY1", capabilities);
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);

        // Start recording.
        ((CanRecordScreen) driver).startRecordingScreen(new IOSStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(30)));


        // Reset App
        driver.resetApp();

        // Login using provided credentials
        driver.findElement(By.id("name")).sendKeys("John Smith");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("login")).click();

        boolean passed = driver.findElement(By.id("logout")).isDisplayed();
        if (passed) {
            System.out.println("Test Passed");
        } else {
            System.out.println("Test Failed");
        }

        String base64String = ((CanRecordScreen)driver).stopRecordingScreen();

        byte[] data = Base64.decodeBase64(base64String);

        String destinationPath="target/filename.mp4";
        Path path = Paths.get(destinationPath);
        Files.write(path, data);

        driver.quit();
    }

    private void IOSTest() { }
}