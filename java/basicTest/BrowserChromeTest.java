package basicTest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class BrowserChromeTest {

    public AndroidDriver driver;
	
    @BeforeMethod
    public void configuresetup() throws InterruptedException, MalformedURLException, URISyntaxException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("emulator-5554");
        options.setPlatformName("Android");
        options.withBrowserName("Chrome");
        options.setChromedriverExecutable("C:\\Rajitha\\Browser 126 version\\chromedriver-win64\\chromedriver.exe");
		
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
	
    @Test
    public void browserTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.google.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q"))).sendKeys("Rahul Shetty Academy");
		
        // Simulate pressing the Enter key
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
	
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
