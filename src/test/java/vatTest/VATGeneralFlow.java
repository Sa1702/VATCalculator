package vatTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import VAT.TestComponent.BaseTest;

public class VATGeneralFlow extends BaseTest {
	String defaultCountry = "United Kingdom";
	String selectedCountry = "Austria";
	int defaultVAT_Austria = 20;
	float priceWithoutVAT = 150;
	float valueAddedTax = 50;
	float priceInclVAT = 300;

	@Test
	public void LaunchDetails() throws IOException {
		List<Integer> defaultVATList = new ArrayList<Integer>();
		defaultVATList.addAll(Arrays.asList(5, 20));
		vat.loadDetails(defaultVATList, defaultCountry);
	}

	@Test
	public void CaclulateUsingPricewihtoutVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		vat.ProvidePriceWithoutVATinput(selectedVATList, priceWithoutVAT, selectedCountry);

	}

	@Test
	public void CaclulateUsingVAT() throws InterruptedException {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		vat.ProvideVATInput(selectedVATList, valueAddedTax, selectedCountry);

	}
	@Test
	public void CaclulateUsingPriceInclVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10, 13, 20));
		vat.ProvidePriceInclVATinput(selectedVATList, priceInclVAT, selectedCountry);

	}
}
