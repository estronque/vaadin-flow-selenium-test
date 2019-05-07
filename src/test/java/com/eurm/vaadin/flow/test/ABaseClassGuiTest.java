package com.eurm.vaadin.flow.test;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

import com.eurm.vaadin.flow.test.ui.UnloggedRoute;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class ABaseClassGuiTest {

	public static final String URL = "http://localhost:";

	@LocalServerPort
	protected int randomPort;

	@Value("${test.browser.gui}")
	protected boolean browserGui;

	public static WebDriver webDriver;
	public TakesScreenshot takesScreenshot;

	@Before
	public void setUp() {
		ChromeOptions capabilities = new ChromeOptions(); 
		capabilities.setCapability("takesScreenshot", true);
		
		WebDriverManager.getInstance(ChromeDriver.class).setup();
		if (!browserGui) { 
			capabilities.addArguments("headless");
			capabilities.addArguments("nogpu");
			
		} 
		webDriver = new ChromeDriver(capabilities);			
		
		webDriver.manage().window().setSize(new Dimension(1920, 1080));
		takesScreenshot = (TakesScreenshot) webDriver;
	}

	@After
	public void tearDown() {
		webDriver.quit();
	}

	/**
	 * Load web page with URL. First Step
	 * @throws Exception
	 */
	public void loadWebPage( String url ) throws Exception {
		webDriver.get(url);
		Thread.sleep(120000);
	}
	
	public WebElement textField(String id) {
		
		//First, find the element that encapsulates the shadow DOM 
		WebElement wrappingElement = webDriver.findElement(By.id(id));

		//Then get shadow root using javascript, and find the first input
		String script = "return arguments[0].shadowRoot.childNodes[2].getElementsByTagName('input')[0];";
		
		return (WebElement) ((JavascriptExecutor) webDriver).executeScript(script, wrappingElement);
	}

	/**
	 * Login test control in HMI app.
	 * 
	 * Send user, send password, and then simulate button click
	 * 
	 * @param user
	 * @param password
	 * @throws Exception
	 */
	public void doLogin( String user, String password ) throws Exception {
		// User
		WebElement loginField = textField(UnloggedRoute.USER_NAME_TEXT_FIELD_ID);
		loginField.clear();
		loginField.sendKeys(user);
		Thread.sleep(500);

		// Password
		WebElement passwordField = textField(UnloggedRoute.PASSWORD_FIELD_ID);
		passwordField.clear();
		passwordField.sendKeys(password);
		Thread.sleep(1000);

		// Button click event
		WebElement buttonLogin = webDriver.findElement(By.id(UnloggedRoute.LOGIN_BUTTON_ID));
		buttonLogin.click();

		Thread.sleep(10000);
	}


	/**
	 * Logout test control in HMI app.
	 * 
	 * Send user, send password, and then simulate button click
	 * @param loadButtonLogoutText 
	 * 
	 * @param user
	 * @param password
	 * @throws Exception
	 */
	public void doLogout() throws Exception {
		
	}

	/**
	 * Extract method
	 * 
	 * @throws Exception
	 */
	public void loadAndlogin (String user, String password) throws Exception {

		loadWebPage(URL + randomPort);

		doLogin(user, password);
	}

}
