package org.virtusa.mobileTesting.android.pageObjectModelCloud;

import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import virtusa.abstractComponent.AbstractionClass;

public class FormPage extends AbstractionClass {

	AndroidDriver driver;

	public FormPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
	private WebElement countryDropDown;

	@AndroidFindBy(className = "android.widget.TextView")
	private List<WebElement> CountriesName;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement nameField;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	private WebElement femaleGender;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
	private WebElement maleGender;

	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement shopOnButton;
	
	@AndroidFindBy(xpath="//android.widget.Toast[1]")
	private WebElement errorMessage;
	
	
	

	public void selectCountryDropDown(String countryNmae) {

		elementToBeClickable(countryDropDown);
		countryDropDown.click();
		scrollingGesture(countryNmae);
		clickOnCountry(countryNmae);
		
		//elementToBeClickable(clickOnCountry);
		//clickOnCountry.click();

	}
	
	public void clickOnCountry(String countryesName) {
		for(WebElement country:CountriesName)
		{
			String countryName=country.getText();
			if(countryName.equalsIgnoreCase(countryesName))
			{
				country.click();
				break;
			}
		}
	}

	public void setName(String name) {

		presenceOfElementLocated(By.id("com.androidsample.generalstore:id/nameField"));
		nameField.sendKeys(name);
	

	}

	public void setGender(String gender) {
		if(gender.contains("female"))
		{
		elementToBeClickable(femaleGender);
		femaleGender.click();
		}
		else
		{
			elementToBeClickable(maleGender);
			maleGender.click();
		}
	}

	public ProductPage clickOnContinue() {
		elementToBeClickable(shopOnButton);
		shopOnButton.click();
		return new ProductPage(driver);

	}
	

	
	public void validateErrorMessage(String errormessage) {
		
		String text=errorMessage.getText();
	//Assert.assertEquals(text, errormessage);
	}

	
	
	
	 
}
		
