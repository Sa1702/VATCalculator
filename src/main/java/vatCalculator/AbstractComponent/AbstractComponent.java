package vatCalculator.AbstractComponent;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import vat.VATpage;

public class AbstractComponent {

	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	public void checkVATRates(List<Integer> VATList) {
		for (WebElement temp : VATpage.VATrates_radio) {
			String VATpercentages = temp.getText();
			String[] VATnumbers = VATpercentages.split("%");
			String VATpercentnumbers = VATnumbers[0].trim();
			int VATvalue = Integer.parseInt(VATpercentnumbers);
			Assert.assertTrue(VATList.contains(VATvalue));
		}
	}

	public void SelectCountry(String selectedcountry) {
		Select sel = new Select(VATpage.country);
		sel.selectByVisibleText(selectedcountry);
		WebElement checkCountry = sel.getFirstSelectedOption();
		String checkCountry_Text = checkCountry.getText();
		Assert.assertTrue(checkCountry_Text.contentEquals(selectedcountry));
		System.out.println("Country Matched");
	}

	public void waitForElementToBeClickable(WebElement valueAddedTax_Radio) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(valueAddedTax_Radio));
	}

}
