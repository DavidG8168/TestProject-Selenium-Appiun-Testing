import com.migcomponents.migbase64.Base64;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.testproject.sdk.drivers.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Android Test Basic Example.
 */
public final class recordAndroid {

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
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.UDID, "4fb8acb2");
        capabilities.setCapability(MobileCapabilityType.APP, "https://github.com/testproject-io/android-demo-app/raw/master/APK/testproject-demo-app.apk");
        AndroidDriver<MobileElement> driver = new AndroidDriver<>("xMOUq-CTwc12Ru7Vjls1syE6uZ8B7vxcv1KAs55XaOY1", capabilities);
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);

        driver.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(30)));

        // Reset App
        driver.resetApp();

        // Login using provided credentials
        driver.findElement(By.id("name")).sendKeys("John Smith");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("login")).click();

        // Notify of wait time.
        System.out.println("Waiting 5 minutes to save video");
        Thread.sleep(300000);


        String base64String = driver.stopRecordingScreen();
        //Decode String To Video With mig Base64.
        byte[] decodedBytes = Base64.decodeFast(base64String.getBytes());
        try {
            FileOutputStream out = new FileOutputStream(
                    "C:\\Users\\goich\\Downloads"+"\\Convert.mp4");
            out.write(decodedBytes);
            out.close();
        } catch (Exception e) {
            System.out.println("Something done goofed");
        }

        // Quitting driver creates the report.
        driver.quit();
    }
}