package vat;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
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
	public static WebElement country;

	@FindBy(xpath = "//label[contains(@for,'VAT')]")
	public static List<WebElement> VATrates_radio;

	@FindBy(id = "VAT_20")
	WebElement defaultVATPercentage;

	@FindBy(xpath = "//label[@for='F1']")
	WebElement priceWithoutVAT;

	@FindBy(xpath = "//label[@for='F2']")
	WebElement valueAddedTax_Radio;

	@FindBy(xpath = "//label[@for='F3']")
	WebElement priceInclVAT_Radio;

	@FindBy(id = "NetPrice")
	WebElement priceWithoutVAT_Text;

	@FindBy(id = "VATsum")
	WebElement valueAddedTax_Text;

	@FindBy(id = "Price")
	WebElement priceInclVAT_Text;

	public void goTo(String url) {
		driver.get(url);
	}

	public void loadDetails(List<Integer> listDefaultVAT, String countryName) {
		Select sel = new Select(country);
		WebElement getCountryName = sel.getFirstSelectedOption();
		String defaultCountry = getCountryName.getText();
		Assert.assertEquals(defaultCountry, countryName);
		checkVATRates(listDefaultVAT);
		// Assert.assertTrue(priceWithoutVAT.isSelected());
		Assert.assertFalse(valueAddedTax_Radio.isSelected());
		Assert.assertFalse(priceInclVAT_Radio.isSelected());
		String text_PWV = priceWithoutVAT_Text.getAttribute("class");
		Assert.assertTrue(text_PWV.contentEquals("vis biginput R W120"));
		String text_VAT = valueAddedTax_Text.getAttribute("class");
		Assert.assertTrue(text_VAT.contentEquals("disabled vis biginput R W120"));
		String text_PIV = priceInclVAT_Text.getAttribute("class");
		Assert.assertTrue(text_PIV.contentEquals("disabled vis biginput R W120"));
	}

	public void ProvidePriceWithoutVATinput(List<Integer> selectedVATList, String selectedcountry,
			String priceWithoutVAT2, String vat, String priceWithVAT) {
		SelectCountry(selectedcountry);
		checkVATRates(selectedVATList);
		priceWithoutVAT_Text.sendKeys(priceWithoutVAT2);
		String text_VAT = valueAddedTax_Text.getAttribute("value");
		Assert.assertEquals(text_VAT, vat);
		System.out.println("Value Added Tax value matched");
		String text_priceInclVAT = priceInclVAT_Text.getAttribute("value");
		Assert.assertEquals(text_priceInclVAT, priceWithVAT);
		System.out.println("Price Incl VAT value matched");
	}

	public void ProvideVATInput(List<Integer> selectedVATList, String country, String priceWithoutVATdata,
			String vatValue, String priceInclVATdata) throws InterruptedException {
		SelectCountry(country);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(250,350)");
		waitForElementToBeClickable(valueAddedTax_Radio);
		valueAddedTax_Radio.click();
		valueAddedTax_Text.sendKeys(vatValue);
		String priceWithoutVAT = priceWithoutVAT_Text.getAttribute("value");
		Assert.assertEquals(priceWithoutVAT, priceWithoutVATdata);
		String text_priceInclVAT = priceInclVAT_Text.getAttribute("value");
		Assert.assertEquals(text_priceInclVAT, priceInclVATdata);
	}

	public void ProvidePriceInclVATinput(List<Integer> selectedVATList, float priceInclVAT, String selectedCountry) {
		SelectCountry(selectedCountry);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(250,350)");
		waitForElementToBeClickable(priceInclVAT_Radio);
		priceInclVAT_Radio.click();
		priceInclVAT_Text.sendKeys(String.valueOf(priceInclVAT));
		String priceWithoutVAT = priceWithoutVAT_Text.getAttribute("value");
		Assert.assertEquals(priceWithoutVAT, "250.00");
		String text_VAT = valueAddedTax_Text.getAttribute("value");
		Assert.assertEquals(text_VAT, "50.00");
	}
}
