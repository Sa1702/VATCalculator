package vat;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import vatCalculator.AbstractComponent.AbstractComponent;

public class VATpage extends AbstractComponent {

	WebDriver driver;

	public VATpage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "select[class='select150']")
	WebElement country;

	@FindBy(xpath = "//label[contains(@for,'VAT')]")
	List<WebElement> VATrates_radio;

	@FindBy(id = "VAT_20")
	WebElement defaultVATPercentage;

	@FindBy(id = "F1")
	WebElement priceWithoutVAT;

	@FindBy(id = "F2")
	WebElement valueAddedTax_Radio;

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
		for (WebElement temp : VATrates_radio) {
			boolean flag = temp.isSelected();
			System.out.println(flag);
		}
		Assert.assertTrue(defaultVATPercentage.isSelected());
		Assert.assertTrue(priceWithoutVAT.isSelected());
		Assert.assertFalse(valueAddedTax_Radio.isSelected());
		Assert.assertFalse(priceInclVAT.isSelected());
		String text_PWV = priceWithoutVAT_Text.getAttribute("class");
		Assert.assertTrue(text_PWV.contentEquals("vis biginput R W120"));
		String text_VAT = valueAddedTax_Text.getAttribute("class");
		Assert.assertTrue(text_VAT.contentEquals("disabled vis biginput R W120"));
		String text_PIV = priceInclVAT_Text.getAttribute("class");
		Assert.assertTrue(text_PIV.contentEquals("disabled vis biginput R W120"));
	}

	public void checkVATRates(List<Integer> VATList) {
		for (WebElement temp : VATrates_radio) {
			String VATpercentages = temp.getText();
			String[] VATnumbers = VATpercentages.split("%");
			String VATpercentnumbers = VATnumbers[0].trim();
			int VATvalue = Integer.parseInt(VATpercentnumbers);
			Assert.assertTrue(VATList.contains(VATvalue));
		}
	}

	public void ProvidePriceWithoutVATinput(List<Integer> selectedVATList, float priceWithoutVAT2, WebDriver driver) {
		float selectedVATPercent_int, valueAddedTax_value, priceInclVAT_value;
		Select sel = new Select(country);
		sel.selectByValue("1");
		String selectedVATPercent = "";
		checkVATRates(selectedVATList);
		for (WebElement temp : VATrates_radio) {
			boolean flag = temp.isSelected();
			System.out.println(flag);
			if (flag == true) {
				selectedVATPercent = temp.getAttribute("value");
			}
		}
		priceWithoutVAT_Text.sendKeys(String.valueOf(priceWithoutVAT2));
		System.out.println(selectedVATPercent);
		selectedVATPercent_int = Float.parseFloat(selectedVATPercent); // 20
		valueAddedTax_value = (selectedVATPercent_int * priceWithoutVAT2) / 100; // 30
		String strValueAddedTax = String.valueOf(valueAddedTax_value);
		String text_VAT = valueAddedTax_Text.getAttribute("value");
		// Assert.assertEquals(strValueAddedTax, text_VAT);
		priceInclVAT_value = priceWithoutVAT2 + valueAddedTax_value;
		String priceInclVAT_value_Str = String.valueOf(priceInclVAT_value);
		String text_priceInclVAT = priceInclVAT_Text.getAttribute("value");
		Assert.assertEquals(text_priceInclVAT, priceInclVAT_value_Str);
	}

	public void ProvideVATInput(List<Integer> selectedVATList, float valueAddedTax2, WebDriver driver2) {
		float selectedVATPercent_int, priceWithoutVAT_value, priceInclVAT_value;
		Select sel = new Select(country);
		sel.selectByValue("1");
		String selectedVATPercent = null;
		checkVATRates(selectedVATList);
		for (WebElement temp : VATrates_radio) {
			boolean flag = temp.isSelected();
			System.out.println(flag);
			if (flag == true) {
				selectedVATPercent = temp.getAttribute("value");
			}
		}
		valueAddedTax_Radio.click();
		waitForElementToBeClickable();
		valueAddedTax_Text.sendKeys(String.valueOf(valueAddedTax2));
		selectedVATPercent_int = Float.parseFloat(selectedVATPercent); // 20
		priceWithoutVAT_value = (100 * valueAddedTax2) / (selectedVATPercent_int); // 30
		String strPriceWithoutVAT_value = String.valueOf(priceWithoutVAT_value);
		String priceWithoutVAT = priceWithoutVAT_Text.getAttribute("value");
		Assert.assertEquals(strPriceWithoutVAT_value, priceWithoutVAT);
		priceInclVAT_value = priceWithoutVAT_value + valueAddedTax2;
		String priceInclVAT_value_Str = String.valueOf(priceInclVAT_value);
		String text_priceInclVAT = priceInclVAT_Text.getAttribute("value");
		Assert.assertEquals(text_priceInclVAT, priceInclVAT_value_Str);
	}

	public void callTest() {
		Select sel = new Select(country);
		sel.selectByValue("1");
		for (WebElement temp : VATrates_radio) {
			System.out.println(temp.isSelected());
		}
	}
}
