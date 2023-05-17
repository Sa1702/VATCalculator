package GlobalBlueTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import GlobalBlue.TestComponent.BaseTest;

public class VATGeneralFlow extends BaseTest {
	String defaultCountry = "United Kingdom";
	String selectedCountry = "Austria";
	float priceWithoutVAT = 150;

	@Test
	public void LaunchDetails() throws IOException {
		List<Integer> defaultVATList = new ArrayList<Integer>();
		defaultVATList.addAll(Arrays.asList(5,20));
		vat.loadDetails(defaultVATList, defaultCountry);
	}

	@Test
	public void CaclulateUsingPricewihtoutVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10,13,20));
		vat.CountrySelection(selectedVATList, priceWithoutVAT, driver);

	}
}
