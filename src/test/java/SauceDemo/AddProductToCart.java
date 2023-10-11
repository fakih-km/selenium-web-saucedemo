package SauceDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertTrue;


public class AddProductToCart {

    WebDriver driver;
    @BeforeTest
    private void init() {
        //initial browser
        System.setProperty("web-driver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chrome.exe");
        driver = new ChromeDriver();
        //maximize browser
        driver.manage().window().maximize();
        //get to homepage
        driver.navigate().to("https://www.saucedemo.com/");

    }

    @Test(priority = 0)
    private void CheckElement()  {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.LoginSuccess();
        //check icon cart
        driver.findElement(By.className("shopping_cart_link"));

        //check title page
        Assert.assertEquals(driver.findElement(By.className("app_logo")).getText(),"Swag Labs");

        //check hamburger menu
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.id("react-burger-menu-btn"))).perform();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"inventory_sidebar_link\"]")).getText(),"All Items");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"about_sidebar_link\"]")).getText(),"About");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]")).getText(),"Logout");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"reset_sidebar_link\"]")).getText(),"Reset App State");

        //check dropdown sort by
        Select dropdownSortBy = new Select(driver.findElement(By.className("product_sort_container")));
        List<WebElement> dropdownOptions = dropdownSortBy.getOptions();
        Assert.assertEquals(dropdownOptions.get(0).getText(),"Name (A to Z)");
        Assert.assertEquals(dropdownOptions.get(1).getText(),"Name (Z to A)");
        Assert.assertEquals(dropdownOptions.get(2).getText(),"Price (low to high)");
        Assert.assertEquals(dropdownOptions.get(3).getText(),"Price (high to low)");

        //check title product index 0 to 3
        List<WebElement> listItemTitle = driver.findElements(By.className("inventory_item_name"));
        Assert.assertEquals(listItemTitle.get(0).getText(),"Sauce Labs Backpack");
        Assert.assertEquals(listItemTitle.get(1).getText(),"Sauce Labs Bike Light");
        Assert.assertEquals(listItemTitle.get(2).getText(),"Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(listItemTitle.get(3).getText(),"Sauce Labs Fleece Jacket");

        //check description product index 0 to 1
        List<WebElement> listItemDesc = driver.findElements(By.className("inventory_item_desc"));
        Assert.assertEquals(listItemDesc.get(0).getText(),"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertEquals(listItemDesc.get(1).getText(),"A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.");

        //check image product indexing use odd (1,3,5,7) because the classes of the parent and child are the same
        List<WebElement> listItemImage = driver.findElements(By.className("inventory_item_img"));
        //System.out.println(listItemImage.get(1).getAttribute("alt"));//check index 1
        Assert.assertEquals(listItemImage.get(1).getAttribute("alt"),"Sauce Labs Backpack");
        Assert.assertEquals(listItemImage.get(3).getAttribute("alt"),"Sauce Labs Bike Light");
        Assert.assertEquals(listItemImage.get(5).getAttribute("alt"),"Sauce Labs Bolt T-Shirt");

        //check price product index 0
        List<WebElement> listItemPrice = driver.findElements(By.className("inventory_item_price"));
        //System.out.println(listItemPrice.get(1).getText());//check index 1
        Assert.assertEquals(listItemPrice.get(0).getText(),"$29.99");
        Assert.assertEquals(listItemPrice.get(1).getText(),"$9.99");
        Assert.assertEquals(listItemPrice.get(2).getText(),"$15.99");

    }

    @Test(priority = 1)
    private void AddToCart(){

        //add to cart in the list product page
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Assert.assertEquals(driver.findElement(By.className("shopping_cart_badge")).getText(),"1");// verify badge in the cart

        //remove product in the list product page
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());// verify badge cart not exist

        //add to cart in the PDP
        driver.findElement(By.id("item_4_title_link")).click();// navigate to PDP
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Assert.assertEquals(driver.findElement(By.className("shopping_cart_badge")).getText(),"1");// verify badge in the cart

        //remove product int the PDP
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());// verify badge cart not exist

        //verify product in the cart page
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        Assert.assertEquals(driver.findElement(By.className("inventory_item_name")).getText(),"Sauce Labs Backpack");
        Assert.assertEquals(driver.findElement(By.className("inventory_item_desc")).getText(),"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertEquals(driver.findElement(By.className("inventory_item_price")).getText(),"$29.99");
        Assert.assertEquals(driver.findElement(By.className("shopping_cart_badge")).getText(),"1");

        // remove product in the cart
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
        assertTrue(driver.findElements(By.className("inventory_item_name")).isEmpty());

    }
    @AfterTest
    private void CloseBrowser(){
        driver.close();
    }
}
