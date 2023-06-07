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
	float priceWithoutVAT = 150;
	float valueAddedTax = 50;

	@Test
	public void LaunchDetails() throws IOException {
		List<Integer> defaultVATList = new ArrayList<Integer>();
		defaultVATList.addAll(Arrays.asList(5,20));
		vat.loadDetails(defaultVATList, defaultCountry);
	}

	@Test(enabled =false)
	public void Test()
	{ 
	vat.callTest();	
	}
	@Test(enabled =false)
	public void CaclulateUsingPricewihtoutVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10,13,20));
		vat.ProvidePriceWithoutVATinput(selectedVATList, priceWithoutVAT, driver);

	}
	
	@Test(enabled =false)
	public void CaclulateUsingVAT() {
		List<Integer> selectedVATList = new ArrayList<Integer>();
		selectedVATList.addAll(Arrays.asList(10,13,20));
		vat.ProvideVATInput(selectedVATList, valueAddedTax, driver);

	}
}
