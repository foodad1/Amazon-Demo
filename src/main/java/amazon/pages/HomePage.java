package amazon.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class HomePage {


	private WebDriver driver;
	private ExtentTest testStep;
	
	public HomePage(WebDriver driver,ExtentTest testStep) {

		this.driver = driver;
		this.testStep=testStep;
		PageFactory.initElements(driver,this);
		//commonMethods= new CommonMethods(driver, testStep);

	}

	@FindBy(xpath="//input[@id='twotabsearchtextbox']")
	private WebElement searchBar;

	@FindBy(xpath="//select[@id='s-result-sort-select']")
	private WebElement sortSelectOptions;

	@FindBy(xpath="//span[@class='a-dropdown-container']")
	private WebElement sortBtn;
	@FindBy(xpath="//div[@class='nav-search-submit nav-sprite']//input")
	private WebElement searchBtn;

	@FindBy(xpath="//input[@id='add-to-cart-button']")
	private WebElement addToCartBtn;

	@FindBy(partialLinkText ="Cart")
	private WebElement cartBtn;
	
	@FindBy(xpath="//span[@class='a-button-inner']//span[@class='a-dropdown-prompt']")
	private WebElement QtyValue;
	

	public void enterSearchValue(String product) {
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(searchBar));
			searchBar.sendKeys(product);
			searchBtn.click();
		} catch(TimeoutException t) {
			testStep.log(LogStatus.FAIL, "Search Bar expected to appear in 20 seconds but not visible");
		} catch(Exception e ) {
			testStep.log(LogStatus.FAIL, "Search Bar element exception "+e.getMessage());
		}
	}

	public void sortProductPreference(String sortingOption) {

		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(sortBtn));
			sortBtn.click();

			Select select = new Select(sortSelectOptions);
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfAllElements(sortSelectOptions));
			select.selectByVisibleText(sortingOption);
			Thread.sleep(1000);

		} catch(TimeoutException t) {
			testStep.log(LogStatus.FAIL, "Search Bar expected to appear in 20 seconds but not visible");
		} catch(Exception e ) {	
			testStep.log(LogStatus.FAIL, "Search Bar element exception "+e.getMessage());
		}
	}

	public void selectItemFromCatalogue(String item) {

		try {
			System.out.println("selectItemFromCatalogue");
			new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains("price-asc-rank"));

			String xpath="//a[@class='a-link-normal a-text-normal']/span[starts-with(text(),'replace')]";
			xpath= xpath.replaceAll("replace", item);
			WebElement ele = driver.findElement(By.xpath(xpath));

			Thread.sleep(500);
			ele.click();
			String parent = driver.getWindowHandle();
			switchToWindow(parent);
			if(driver.getCurrentUrl().contains(item)) {
				testStep.log(LogStatus.PASS, "Product opened in new page successfully");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(TimeoutException t) {
			testStep.log(LogStatus.FAIL, "Sorting Item options expected to appear in 20 seconds but not visible");
		} catch(Exception e ) {	
			testStep.log(LogStatus.FAIL, "element exception "+e.getMessage());
		}

	}

	public void switchToWindow(String parent) {

		for(String window:driver.getWindowHandles()) {
			if(!(window.equals(parent))) {
				driver.switchTo().window(window);
				break;
			}
		}
	}

	public void clickAddToCartBtn() {

		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(addToCartBtn));
			addToCartBtn.click();

		} catch(TimeoutException t) {
			testStep.log(LogStatus.FAIL, "addToCart Btn to appear in 20 seconds but not visible");
		} catch(Exception e ) {	
			testStep.log(LogStatus.FAIL, "addToCart Btn element exception "+e.getMessage());
		}

	}

	public void clickCartLink() {

		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains("newItems"));
			cartBtn.click();
		} catch(TimeoutException t) {
			testStep.log(LogStatus.FAIL, "Cart Link to appear in 20 seconds but not visible");
		} catch(Exception e ) {	
			testStep.log(LogStatus.FAIL, cartBtn+" Cart Btn element exception "+e.getMessage());
		}

	}
	
	public void verifyCartItems(String quantity) {
		
		if(QtyValue.getText().trim().equalsIgnoreCase(quantity)) {
			testStep.log(LogStatus.PASS, "Quantity value verified successfully");
		} else {
			testStep.log(LogStatus.FAIL, "Quantity value  not verified expected "+quantity+"actual cart items "+QtyValue.getText().toString());
		}
	}
	
}
