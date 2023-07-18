package vatTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@Test(dataProvider = "getData_PriceWihtoutVAT")
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
		vat.ProvideVATInput(selectedVATList, data.get(1), data.get(2), data.get(3), data.get(4));

	}

	@Test
	public void CaclulateUsingPriceInclVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		vat.ProvidePriceInclVATinput(selectedVATList, priceInclVAT, selectedCountry);

	}

	@DataProvider
	public String[][] getData_PriceWihtoutVAT() {
		String data[][] = new String[1][4];
		data[0][0] = "Austria";
		data[0][1] = "150.00";
		data[0][2] = "30.00";
		data[0][3] = "180.00";
		return data;
	}
}
