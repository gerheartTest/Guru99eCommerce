package End2EndProjects.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest extends BaseTest
{
    
    @Test
    public void mobileTest()
    {
    	// Verify Title of the page
    	String actual_homeTitle = driver.findElement(By.cssSelector(".page-title h2")).getText();
    	Assert.assertTrue(actual_homeTitle.contains(EXPECT_HOMETITLE));
    	
    	//Click on 'MOBILE' menu
    	driver.findElement(By.linkText("MOBILE")).click();
    	
    	//Verify Title of the page
    	String actual_mobileTitle = driver.findElement(By.cssSelector(".page-title h1")).getText();
    	Assert.assertEquals(actual_mobileTitle, EXPECT_MOBILETITLE);
    	
    	//In the list of all mobile, select 'SORT BY' dropdown as 'name'
    	Select ddlSortBy = new Select(driver.findElement(By.xpath("//select[@title='Sort By']")));
    	ddlSortBy.selectByVisibleText("Name");
    	
    	//Verify all products are sorted by name
    	List<WebElement> ele_mobileProducts = driver.findElements(By.cssSelector(".products-grid .item.last"));
    	List<String> actual_itemList = ele_mobileProducts.stream().map(s->s.findElement(By.cssSelector(".product-name")).getText()).toList();
    	List<String> expect_itemList = actual_itemList.stream().sorted().collect(Collectors.toList());
    	
    	Assert.assertEquals(actual_itemList, expect_itemList);
    	
    	//Get Cost of Sony Xperia mobile
    	WebElement ele_sony = ele_mobileProducts.stream().filter(s->s.findElement(By.cssSelector(".product-name")).getText().contains("SONY XPERIA")).findFirst().orElse(null);
    	String sonyPriceInList = ele_sony.findElement(By.cssSelector(".price")).getText();
    	
    	//Click on Sony Xperia mobile
    	ele_sony.findElement(By.cssSelector(".product-name")).click();
    	
    	//Compare Cost from Mobile list Page to Product Details Page
    	String sonyPriceInDetails = driver.findElement(By.cssSelector(".price")).getText();
    	Assert.assertEquals(sonyPriceInList, sonyPriceInDetails);
    }
}
