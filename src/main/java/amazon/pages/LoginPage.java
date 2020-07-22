package amazon.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class LoginPage {

	private WebDriver driver;
	private ExtentTest testStep;

	public LoginPage(WebDriver driver,ExtentTest testStep) {

		this.driver = driver;
		this.testStep=testStep;
		PageFactory.initElements(driver,this);
		//commonMethods= new CommonMethods(driver, testStep);

	}

	@FindBy(xpath="//a[@id='nav-link-accountList']")
	private WebElement signInLink;

	@FindBy(xpath="//input[@id='ap_email']")
	private WebElement emailInput;

	@FindBy(xpath="//input[@id='continue']")
	private WebElement continueBtn;

	@FindBy(xpath="//input[@id='ap_password']")
	private WebElement passwordInput;//

	@FindBy(xpath="//input[@id='signInSubmit']")
	private WebElement submitBtn;
	
	@FindBy(xpath="//a[@id='nav-item-signout']")
	private WebElement signOutLink;
	

	public void loginUser(String id,String pwd) {

		signInLink.click();
		emailInput.sendKeys(id);
		testStep.log(LogStatus.PASS, "User Email "+id+"entered successfully");
		continueBtn.click();
		passwordInput.sendKeys(pwd);
		testStep.log(LogStatus.PASS, "User Password entered successfully");
		submitBtn.click();

	}

	public void logout() {

		Actions action = new Actions(driver);
		action.moveToElement(signInLink).build().perform();
		
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(signOutLink));
		if(signOutLink.isDisplayed()) {
			signOutLink.click();
			
			if(emailInput.isDisplayed()) {
				testStep.log(LogStatus.PASS, "User logout successfully");
			}
		} else {
			testStep.log(LogStatus.FAIL, "User not logged in ");
		}
		
	}

}
