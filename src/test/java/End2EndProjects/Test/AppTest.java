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
    	Assert.assertEquals(driver.getTitle(), EXPECT_HOMETITLE);
    	
    	//Click on 'MOBILE' menu
    	driver.findElement(By.linkText("MOBILE")).click();
    	
    	//Verify Title of the page
    	Assert.assertEquals(driver.getTitle(), EXPECT_MOBILETITLE);
    	
    	//In the list of all mobile, select 'SORT BY' dropdown as 'name'
    	Select ddlSortBy = new Select(driver.findElement(By.xpath("//select[@title='Sort By']")));
    	ddlSortBy.selectByVisibleText("Name");
    	
    	//Verify all products are sorted by name
    	List<WebElement> ele_prodName= driver.findElements(By.xpath("//h2[@class='product-name']"));
    	List<String> actual_itemList = ele_prodName.stream().map(s->s.getText()).toList();
    	System.out.println(actual_itemList);
    	List<String> expect_itemList = actual_itemList.stream().sorted().collect(Collectors.toList());
    	System.out.println(expect_itemList);
    	
    	Assert.assertEquals(actual_itemList, expect_itemList);
    	
    	
    }
}
