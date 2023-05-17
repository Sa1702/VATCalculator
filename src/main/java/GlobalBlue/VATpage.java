package GlobalBlue;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class VATpage {

	WebDriver driver;

	public VATpage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "select[class='select150']")
	WebElement country;

	@FindBy(xpath = "//input[@name='VAT']")
	List<WebElement> VATrates_radio;

	@FindBy(id = "VAT_20")
	WebElement defaultVATPercentage;

	@FindBy(id = "F1")
	WebElement priceWithoutVAT;

	@FindBy(id = "F2")
	WebElement valueAddedTax;

	@FindBy(id = "F3")
	WebElement priceInclVAT;

	@FindBy(id = "NetPrice")
	WebElement priceWithoutVAT_Text;

	@FindBy(id = "VATsum")
	WebElement valueAddedTax_Text;

	@FindBy(id = "Price")
	WebElement priceInclVAT_Text;

	public void goTo(WebDriver driver) {
		driver.get("https://www.calkoo.com/en/vat-calculator");
	}

	public void loadDetails(List<Integer> listDefaultVAT, String countryName) {
		Select sel = new Select(country);
		WebElement getCountryName = sel.getFirstSelectedOption();
		String defaultCountry = getCountryName.getText();
		Assert.assertEquals(defaultCountry, countryName);
		checkVATRates(listDefaultVAT);
		Assert.assertTrue(defaultVATPercentage.isSelected());
		Assert.assertTrue(priceWithoutVAT.isSelected());
		Assert.assertFalse(valueAddedTax.isSelected());
		Assert.assertFalse(priceInclVAT.isSelected());
		Assert.assertTrue(priceWithoutVAT_Text.isEnabled());
		// Assert.assertFalse(valueAddedTax_Text.isEnabled());
		// Assert.assertFalse(priceInclVAT_Text.isEnabled());
	}

	public void checkVATRates(List<Integer> selectedVATList) {
		for (int i = 0; i < VATrates_radio.size(); i++) {
			System.out.println("Hi");
			String VATpercentages = VATrates_radio.get(i).getText();
			String[] VATnumbers = VATpercentages.split("%");
			String VATpercentnumbers = VATnumbers[0].trim();
			System.out.println("Hi1");
			int VATvalue = Integer.parseInt(VATpercentnumbers);
			Assert.assertTrue(selectedVATList.contains(VATvalue));
			System.out.println("Hi3");
		}
	}

	public void CountrySelection(List<Integer> selectedVATList, float priceWithoutVAT2,WebDriver driver) {
		float selectedVATPercent_int,valueAddedTax_value,priceInclVAT_value;
		Select sel = new Select(country);
		sel.selectByValue("1");
		String selectedVATPercent="";
		//checkVATRates(selectedVATList);
		for (WebElement temp : VATrates_radio) {
			boolean flag = temp.isSelected();
			System.out.println(flag);
			if (flag == true) {	
				selectedVATPercent=temp.getAttribute("value");
			}
		}
		priceWithoutVAT_Text.sendKeys(String.valueOf(priceWithoutVAT2));
		selectedVATPercent_int=Float.parseFloat(selectedVATPercent);	//20
		valueAddedTax_value=(selectedVATPercent_int*priceWithoutVAT2)/100; //30
		String strValueAddedTax= String.valueOf(valueAddedTax_value);
		String text_VAT=valueAddedTax_Text.getAttribute("value");
		//Assert.assertEquals(strValueAddedTax, text_VAT);
		priceInclVAT_value=priceWithoutVAT2+valueAddedTax_value;
		String priceInclVAT_value_Str = String.valueOf(priceInclVAT_value);
		String text_priceInclVAT= priceInclVAT_Text.getAttribute("value");
		Assert.assertEquals(text_priceInclVAT, priceInclVAT_value_Str);
	}

}
