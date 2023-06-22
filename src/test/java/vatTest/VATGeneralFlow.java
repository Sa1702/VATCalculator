package vatTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import VAT.TestComponent.BaseTest;

public class VATGeneralFlow extends BaseTest {
	String defaultCountry = "United Kingdom";
	String selectedCountry = "Austria";
	int defaultVAT_Austria = 20;
	float valueAddedTax = 50;
	float priceInclVAT = 300;

	@Test
	public void LaunchDetails() throws IOException {
		List<Integer> defaultVATList = new ArrayList<Integer>();
		defaultVATList.addAll(Arrays.asList(5, 20));
		vat.loadDetails(defaultVATList, defaultCountry);
	}

	@Test(enabled = false)
	public void CaclulateUsingPricewihtoutVAT(String dpCountry, String priceWithoutVAT, String valueAddedTax,
			String priceWithVAT) {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		vat.ProvidePriceWithoutVATinput(selectedVATList, dpCountry, priceWithoutVAT, valueAddedTax, priceWithVAT);

	}

	@Test
	public void CaclulateUsingVAT() throws InterruptedException, IOException {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		ArrayList<String> data = getDataFromExcel("getDataVAT");
		vat.ProvideVATInput(selectedVATList,data.get(1), data.get(2), data.get(3),data.get(4));

	}

	@Test(enabled = false)
	public void CaclulateUsingPriceInclVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		vat.ProvidePriceInclVATinput(selectedVATList, priceInclVAT, selectedCountry);

	}

	@DataProvider
	public String[][] getData_PriceWihtoutVAT() {
		String data[][] = new String[2][4];
		data[0][0] = "Austria";
		data[0][1] = "150.00";
		data[0][2] = "30.00";
		data[0][3] = "180.00";

		data[1][0] = "Austria";
		data[1][1] = "125.75";
		data[1][2] = "25.15";
		data[1][3] = "150.90";

		return data;
	}

	public ArrayList<String> getDataFromExcel(String testCaseName) throws IOException {
		System.out.println(testCaseName);
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
						System.out.println("TestCase Name matched");
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
		System.out.println(inputData);
		workbook.close();
		return inputData;
	}
}
