package SauceDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.testng.Assert.assertEquals;

public class LoginPage {
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

    @Test (priority = 0)
    private void CheckElement(){
        //check title
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]")).getText(),"Swag Labs");
        //check placeholder username
        Assert.assertEquals(driver.findElement(By.id("user-name")).getAttribute("placeholder"),"Username");
        //check placeholder password
        Assert.assertEquals(driver.findElement(By.id("password")).getAttribute("placeholder"),"Password");
        //check btn login
        Assert.assertEquals(driver.findElement(By.id("login-button")).getAttribute("value"),"Login");
        // check color background button
        assertEquals(Color.fromString(driver.findElement(By.className("btn_action")).getCssValue("background-color")).asHex(), "#3ddc91");
        // check color font button
        assertEquals(Color.fromString(driver.findElement(By.className("btn_action")).getCssValue("color")).asHex(), "#132322");
    }

    @Test (priority = 1)
    private void LoginFailed(){
        //click button login
        driver.findElement(By.id("login-button")).click();
        //check error message username null
        assertEquals(driver.findElement(By.cssSelector("body h3")).getText(),"Epic sadface: Username is required");
        driver.findElement(By.className("error-button")).click();
        driver.findElement(By.id("user-name")).sendKeys("wrong_username");
        driver.findElement(By.id("login-button")).click();
        //check error message password null
        assertEquals(driver.findElement(By.cssSelector("body h3")).getText(),"Epic sadface: Password is required");
        driver.findElement(By.className("error-button")).click();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        //check error message wrong values
        assertEquals(driver.findElement(By.cssSelector("body h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
        driver.findElement(By.className("error-button")).click();
        //clear text input
        driver.findElement(By.id("user-name")).clear();

    }

    @Test(priority = 2)
    private void LoginSuccess(){
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.className("shopping_cart_link"));
    }

    @AfterTest
    private void CloseBrowser(){
        driver.close();
    }
}
