package SauceDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckoutFlow {
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
    public void AddProductToCart1(){

        // login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.LoginSuccess();

        // add product to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        // go to cart page
        driver.findElement(By.className("shopping_cart_link")).click();
        // get product name
        String productName1 = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();
        String productName2 = driver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div")).getText();
        //System.out.println(productName1);

        // get product desc
        String productDesc1 = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[1]")).getText();
        String productDesc2 = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[1]")).getText();

        // get product price
        String productPrice1 = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/div")).getText();
        String productPrice2 = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[2]/div")).getText();

        // go to your information page
        driver.findElement(By.id("checkout")).click();
    }



    @Test(priority = 1)
    public void CheckElementYourInformation(){

        // check text title
        Assert.assertEquals(driver.findElement(By.className("title")).getText(),"Checkout: Your Information");

        // check placeholder first name element
        Assert.assertEquals(driver.findElement(By.id("first-name")).getAttribute("placeholder"), "First Name");

        // check placeholder last name element
        Assert.assertEquals(driver.findElement(By.id("last-name")).getAttribute("placeholder"),"Last Name");

        // check placeholder postal code element
        Assert.assertEquals(driver.findElement(By.id("postal-code")).getAttribute("placeholder"),"Zip/Postal Code");

        // check button cancel element
        Assert.assertEquals(driver.findElement(By.id("cancel")).getText(),"Cancel");

        // check button continue element
        Assert.assertEquals(driver.findElement(By.id("continue")).getAttribute("value"),"Continue");
    }
    @Test(priority = 2)
    private void InputYourInformation(){

        // Input first name
        driver.findElement(By.id("first-name")).sendKeys("first name selenium");

        // Input last name
        driver.findElement(By.id("last-name")).sendKeys("last name selenium");

        // Input postal code
        driver.findElement(By.id("postal-code")).sendKeys("3113");

        // click button continue
        driver.findElement(By.id("continue")).click();
    }
    @Test (priority = 3)
    public void CheckSummary(){


//        System.out.println(productName1);
        // check product name
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText(),"Sauce Labs Backpack");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div")).getText(),"Sauce Labs Bike Light");

        // check desc product
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[3]/div[2]/div[1]")).getText(),"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[4]/div[2]/div[1]")).getText(),"A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.");

        // check price product
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[3]/div[2]/div[2]/div")).getText(),"$29.99");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[4]/div[2]/div[2]/div")).getText(),"$9.99");

        // click finish
        driver.findElement(By.id("finish")).click();

        // complete page
        // check element
        driver.findElement(By.className("pony_express"));
        Assert.assertEquals(driver.findElement(By.className("complete-header")).getText(),"Thank you for your order!");
        Assert.assertEquals(driver.findElement(By.className("complete-text")).getText(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        Assert.assertEquals(driver.findElement(By.id("back-to-products")).getText(),"Back Home");

        // click back home
        driver.findElement(By.id("back-to-products")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
    }

    @AfterTest
    public void CloseBrowser (){
        driver.close();
    }
}
