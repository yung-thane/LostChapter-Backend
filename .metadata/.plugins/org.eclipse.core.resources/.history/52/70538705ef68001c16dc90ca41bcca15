package com.revature.models.components;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginComponent {
	
	private WebDriver driver;
	private WebDriverWait wdw; 
	
	@FindBy(xpath = "")
	private WebElement usernameField;
	
	@FindBy(xpath = "")
	private WebElement passwordField; 
	
	@FindBy(xpath = "")
	private WebElement loginButton;
	
	@FindBy(xpath = "")
	private WebElement forgotPasswordLink;
	
	@FindBy(xpath = "")
	private WebElement createAccountButton; 
	
	
	public LoginComponent(WebDriver driver) {
		
		this.driver = driver; 
		wdw = new WebDriverWait(this.driver, 2);
		
		PageFactory.initElements(this.driver, this);

		
		
		
	}
	
	
	public String getUsernameText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(usernameField)).getText();
		
	}
	
	public void setUsernameText(String username) {
		
		wdw.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
		
	}
	
	public String getPasswordText() {
		
		return wdw.until(ExpectedConditions.visibilityOf(passwordField)).getText();
		
		
	}
	
	public void setPasswordText(String password) {
		
		wdw.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
		
	}
	
	public void clickLoginButton() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
		
	}
	
	public void clickForgotPasswordButton() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
		
	}
	
	public void clickCreateAccountButton() {
		
		wdw.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
		
	}
	

}
