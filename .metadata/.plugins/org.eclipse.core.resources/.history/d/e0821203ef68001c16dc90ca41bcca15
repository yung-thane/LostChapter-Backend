package com.revature.models.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupComponent {

	private WebDriver driver;
	private WebDriverWait wdw; 
	
	@FindBy(xpath = "")
	private WebElement firstnameField;
	
	@FindBy(xpath = "")
	private WebElement lastnameField;
	
	@FindBy(xpath = "")
	private WebElement ageField;
	
	@FindBy(xpath = "")
	private WebElement usernameField;
	
	@FindBy(xpath = "")
	private WebElement emailField; 
	
	@FindBy(xpath = "")
	private WebElement passwordField;
	
	@FindBy(xpath = "")
	private WebElement roleDropdown;
	
	@FindBy(xpath = "")
	private WebElement adminRole;
	
	@FindBy(xpath = "")
	private WebElement userRole; 
	
	@FindBy(xpath = "")
	private WebElement signupButton; 
	
	@FindBy(xpath = "")
	private WebElement loginLink;
	
	public SignupComponent(WebDriver driver) {
		
		this.driver = driver; 
		this.wdw = new WebDriverWait(this.driver, 2);
		
		PageFactory.initElements(this.driver, this);
		
	}
	
	public String getFirstnameText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(firstnameField)).getText();
		
	}
	
	public void setFirstnameText(String firstname) {
		
		wdw.until(ExpectedConditions.visibilityOf(firstnameField)).sendKeys(firstname);
		
	}
	
	public String getLastnameText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(lastnameField)).getText();
		
	}
	
	public void setLastnameText(String lastname) {
		
		wdw.until(ExpectedConditions.visibilityOf(lastnameField)).sendKeys(lastname);
		
	}
	
	public String getAgeText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(ageField)).getText();
		
	}
	
	public void setAgeText(String age) {
		
		wdw.until(ExpectedConditions.visibilityOf(ageField)).sendKeys(age);
		
	}
	
	public String getUsernameText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(usernameField)).getText();
		
	}
	
	public void setUsernameText(String username) {
		
		wdw.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
		
	}
	
	public String getEmailText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(emailField)).getText();
		
	}
	
	public void setEmailText(String email) {
		
		wdw.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
		
	}
	
	public String getPasswordText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(passwordField)).getText();
		
	}
	
	public void setPasswordText(String password) {
		
		wdw.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
		
	}
	
	public void clickRoleSelect() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(roleDropdown)).click();
		
	}
	
	public void clickAdminRole() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(adminRole)).click();
		
	}
	
	public void clickUserRole() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(userRole)).click();
		
	}
	
	public void clickSignupButton() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(signupButton)).click();
		
	}
	
	public void clickLoginLink() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
