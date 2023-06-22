package VAT.TestComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import vat.VATpage;

public class BaseTest {
	public WebDriver driver;
	public VATpage vat;
	public String url;

	public WebDriver InitializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//vat//resources//GlobalProperty.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		if (browserName.contains("chrome")) {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(option);
		}
		url = prop.getProperty("url");
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
		driver.manage().deleteAllCookies();
		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public VATpage LaunchApplication() throws IOException {
		driver = InitializeDriver();
		vat = new VATpage(driver);
		vat.goTo(url);
		return vat;
	}
	@AfterMethod
	public void closingApplication() {
		driver.close();
	}
}
