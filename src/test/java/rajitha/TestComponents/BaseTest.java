package rajitha.TestComponents;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {

	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public ExtentReports extent;
	
	@BeforeClass(alwaysRun=true)
	public void configuresetup() throws InterruptedException, URISyntaxException, IOException {
		service = new AppiumServiceBuilder()
			.withAppiumJS(new File("C:\\Users\\rajit\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
			.withIPAddress("127.0.0.1")
			.usingPort(4723)
			.withTimeout(Duration.ofSeconds(240))
			.build();
		service.start();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void startApp() throws InterruptedException, URISyntaxException, IOException {
		UiAutomator2Options option = new UiAutomator2Options();
		option.setDeviceName("emulator-5554");
		option.setPlatformName("Android");
		option.setApp("C:\\Rajitha\\Mobile Testing\\Appium\\src\\test\\java\\resources\\General-Store.apk");
		
		driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), option);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Thread.sleep(3000);
		
		//startAppActivity("com.androidsample.generalstore/com.androidsample.generalstore.MainActivity");
	}
	
	@AfterMethod(alwaysRun=true)
	public void endApp() {
		driver.quit();
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
		if (service != null) {
			service.stop();
		}
	}
	
	
	public void longPressGesture(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", 
			ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));
	}
	
	public void scrollingGesture(Object text) {
		driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
	}
	
	public void scrollingtoEndAction() {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
				"left", 100, "top", 100, "width", 200, "height", 200,
				"direction", "down",
				"percent", 1.0
			));
		} while (canScrollMore);
	}
	
	public void scrollingtoUpAction() {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
				"left", 100, "top", 100, "width", 200, "height", 200,
				"direction", "up",
				"percent", 1.0
			));
		} while (canScrollMore);
	}
	
	public void swipingGesture(WebElement ele, String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			"elementId", ((RemoteWebElement) ele).getId(), "direction", direction, "percent", 0.25));
	}
	
	public void startAppActivity(String appActivity) {
		((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of("intent", appActivity));
	}
	
	public List<HashMap<Object, Object>> getJsonDataToMap(String jsonfilepath) throws IOException {
		String jsonContent = FileUtils.readFileToString(
			new File(System.getProperty("user.dir") + jsonfilepath),
			StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<Object, Object>>>() {});
	}
	
	public String getScreenshot(String testCaseName, AppiumDriver driver2) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver2;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destination));
		return destination;
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<Object, Object>> data = getJsonDataToMap("\\src\\test\\java\\rajitha\\TestData\\eCommerce.json");
		Object[][] dataProvider = new Object[data.size()][1];
		for (int i = 0; i < data.size(); i++) {
			dataProvider[i][0] = data.get(i);
		}
		return dataProvider;
	}
}
