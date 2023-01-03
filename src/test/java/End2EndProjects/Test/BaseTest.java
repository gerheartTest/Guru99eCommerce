package End2EndProjects.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	
	String url = "http://live.techpanda.org/index.php/";
	
	String EXPECT_HOMETITLE = "THIS IS DEMO SITE FOR";
	String EXPECT_MOBILETITLE = "MOBILE";
	
	public WebDriver initializeDriver()
	{
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		
		return data;
	}
	
	@BeforeMethod(alwaysRun=true)
	public void launchApplication() throws IOException
	{
		driver = initializeDriver();
		driver.get(url);
		
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
	
}
