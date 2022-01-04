package com.revature.models.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutComponent {
	
	private WebDriver driver; 
	private WebDriverWait wdw; 
	
	@FindBy(xpath = "")
	private WebElement paymentDropdown;
	
	@FindBy(xpath = "//input[@id='card-number-input']")
	private WebElement cardNumberField;
	
	@FindBy(xpath = "//input[@id='experiation-date-month']")
	private WebElement cardExpirationMonthField;

	@FindBy(xpath = "//input[@id='experiation-date-year']")
	private WebElement cardExpirationYearField;
	
	@FindBy(xpath = "")
	private WebElement personalInfoDropdown;
	
	@FindBy(xpath = "//input[@id='ccv-input']")
	private WebElement cardCCV;
	
	@FindBy(xpath = "//input[@id='cardholder-name-input']")
	private WebElement cardholderName;
	
	@FindBy(xpath = "//input[@id='address-input-street']")
	private WebElement streetName;
	
	@FindBy(xpath = "//input[@id='first-name-input']")
	private WebElement firstName;
	
	@FindBy(xpath = "//input[@id='last-name-input']")
	private WebElement lastName;
	
	@FindBy(xpath = "//body[1]/href=\"https:[1]/app-root[1]/"
			+ "app-checkout[1]/div[1]/div[1]/mat-card[3]/div[1]"
			+ "/mat-radio-group[1]/mat-radio-button[1]/label[1]/span[1]/span[1]")
	private WebElement deliveryOptionOne;
	
	@FindBy(xpath = "//body[1]/href=\"https:[1]/app-root[1]/"
			+ "app-checkout[1]/div[1]/div[1]/mat-card[3]/div[1]"
			+ "/mat-radio-group[1]/mat-radio-button[2]/label[1]/span[1]/span[1]")
	private WebElement deliveryOptionTwo;
	
	@FindBy(xpath = "//button[contains(text(),'Purchase Order')]")
	private WebElement buttonPurchase;
	
	@FindBy(xpath = "//button[contains(text(),'Back to Cart')]")
	private WebElement buttonBackToCart;
	
	public CheckoutComponent(WebDriver driver) {
		
		this.driver = driver;
		wdw = new WebDriverWait(this.driver, 2);
		
		PageFactory.initElements(this.driver, this);;
	}
	
	public String getCardNumberField() {
		return wdw.until(ExpectedConditions.visibilityOf(cardNumberField)).getText();
	}
	
	public void setCardNumberField(String cardNumber) {
		wdw.until(ExpectedConditions.visibilityOf(cardNumberField)).sendKeys(cardNumber);
	}


}
