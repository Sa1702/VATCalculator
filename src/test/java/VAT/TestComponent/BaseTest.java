package VAT.TestComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
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
	public ArrayList<String> getDataFromExcel(String testCaseName) throws IOException {
		ArrayList<String> inputData = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("//Users//Sasi/Workspace//Saranya//VAT//VATCalculator.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("TestData")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> getRows = sheet.rowIterator();
				Row firstRow = getRows.next();
				Iterator<Cell> getColumns = firstRow.cellIterator();
				int k = 0;
				int column = 0;
				while (getColumns.hasNext()) {
					Cell value = getColumns.next();
					if (value.getStringCellValue().equalsIgnoreCase("TestCase")) {
						column = k;
					}
					k++;
				}
				while (getRows.hasNext()) {
					Row iterateRow = getRows.next();
					if (iterateRow.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> dataColumn = iterateRow.cellIterator();
						while (dataColumn.hasNext()) {
							Cell columnValue = dataColumn.next();
							if (columnValue.getCellType() == CellType.STRING) {

								inputData.add(columnValue.getStringCellValue());

							} else {
								inputData.add(NumberToTextConverter.toText(columnValue.getNumericCellValue()));
							}
						}
					}
				}
			}
		}
		workbook.close();
		return inputData;
	}
	@AfterMethod
	public void closingApplication() {
		driver.close();
	}
}
