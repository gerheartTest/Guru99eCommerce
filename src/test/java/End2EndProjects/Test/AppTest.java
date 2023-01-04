package End2EndProjects.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest extends BaseTest
{

	@Test
	public void TestCase1()
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
	}

	@Test
	public void TestCase2()
	{
		//Click on 'MOBILE' menu
		driver.findElement(By.linkText("MOBILE")).click();

		//Get Cost of Sony Xperia mobile
		List<WebElement> ele_mobileProducts = driver.findElements(By.cssSelector(".products-grid .item.last"));
		WebElement ele_sony = ele_mobileProducts.stream().filter(s->s.findElement(By.cssSelector(".product-name")).getText().contains("SONY XPERIA")).findFirst().orElse(null);
		String sonyPriceInList = ele_sony.findElement(By.cssSelector(".price")).getText();

		//Click on Sony Xperia mobile
		ele_sony.findElement(By.cssSelector(".product-name")).click();

		//Compare Cost from Mobile list Page to Product Details Page
		String sonyPriceInDetails = driver.findElement(By.cssSelector(".price")).getText();
		Assert.assertEquals(sonyPriceInList, sonyPriceInDetails);
	}

	@Test
	public void TestCase3()
	{
		//Click on 'MOBILE' menu
		driver.findElement(By.linkText("MOBILE")).click();

		//'ADD TO CART' Sony Xperia Mobile
		List<WebElement> ele_mobileProducts = driver.findElements(By.cssSelector(".products-grid .item.last"));
		WebElement ele_sony = ele_mobileProducts.stream().filter(s->s.findElement(By.cssSelector(".product-name")).getText().contains("SONY XPERIA")).findFirst().orElse(null);
		ele_sony.findElement(By.cssSelector(".btn-cart")).click();

		//Change 'QTY' value to 1000 and click 'UPDATE' button
		driver.findElement(By.cssSelector(".product-cart-actions .input-text")).clear();
		driver.findElement(By.cssSelector(".product-cart-actions .input-text")).sendKeys("1000");
		driver.findElement(By.cssSelector(".product-cart-actions .btn-update")).click();

		//Verify error message
		eWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error-msg")));
		String actual_errorMessage = driver.findElement(By.cssSelector(".error-msg")).getText();
		Assert.assertEquals(actual_errorMessage, EXPECT_ERRORMESSAGE);

		//Click on 'EMPTY CART' link in the footer of list of all mobiles
		driver.findElement(By.cssSelector(".btn-empty")).click();

		//Verify cart is empty
		String actual_noItemMsg = driver.findElement(By.cssSelector(".page-title")).getText();
		Assert.assertEquals(actual_noItemMsg, EXPECT_NOITEMMSG);
	}

	@Test
	public void TestCase4() throws InterruptedException
	{
		//Click on 'MOBILE' menu
		driver.findElement(By.linkText("MOBILE")).click();

		//Click 'Add to Compare' for Sony Xperia and IPHONE
		List<WebElement> eleLst_mobileProducts = driver.findElements(By.cssSelector(".products-grid .item.last"));
		WebElement ele_sony = eleLst_mobileProducts.stream().filter(s->s.findElement(By.cssSelector(".product-name")).getText().contains("SONY XPERIA")).findFirst().orElse(null);
		EXPECT_LSTPRODUCTS.add("SONY XPERIA");
		ele_sony.findElement(By.cssSelector(".link-compare")).click();
		eWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".products-grid .item.last")));
		
		List<WebElement> eleLst_mobileProducts1 = driver.findElements(By.cssSelector(".products-grid .item.last"));
		WebElement ele_iphone = eleLst_mobileProducts1.stream().filter(s->s.findElement(By.cssSelector(".product-name")).getText().contains("IPHONE")).findFirst().orElse(null);
		EXPECT_LSTPRODUCTS.add("IPHONE");
		ele_iphone.findElement(By.cssSelector(".link-compare")).click();

		//Click on 'COMPARE' button
		driver.findElement(By.cssSelector(".block-compare .button")).click();

		//Verify pop-up window and check that the products are reflected in it
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator(); //[parentTab],[childTab]
		String parentHandle = it.next();
		String childHandle = it.next();
		driver.switchTo().window(childHandle);
		
		List<WebElement> eleLst_prodName = driver.findElements(By.cssSelector(".product-name"));
		List<String> actual_lstProducts = eleLst_prodName.stream().map(s->s.getText()).toList();
		Assert.assertEquals(actual_lstProducts, EXPECT_LSTPRODUCTS);
		
		//Close the Pop up Window
		driver.findElement(By.cssSelector(".buttons-set .button")).click();
		driver.switchTo().window(parentHandle);
		
	}
}
