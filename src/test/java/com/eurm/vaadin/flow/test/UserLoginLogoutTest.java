package com.eurm.vaadin.flow.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class UserLoginLogoutTest extends ABaseClassGuiTest {

	@Test
	public void userLoginAndLogoutTest() throws Exception {
		loadAndlogin("user", "user");
		
		File screenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenShot, new File("testSnapshots/" + UserLoginLogoutTest.class.getSimpleName() + ".png"));

		doLogout();
	}
	
}
