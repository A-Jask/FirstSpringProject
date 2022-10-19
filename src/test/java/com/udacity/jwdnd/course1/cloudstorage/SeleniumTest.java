package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/login");

//        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/signup']")));
//        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/signup']")));

        // get to sign-up and sign-up a new user
        driver.findElement(By.xpath("//a[@href='/signup']")).click();
        WebElement inputField = driver.findElement(By.name("firstName"));
        inputField.sendKeys("Bob");
        inputField = driver.findElement(By.name("lastName"));
        inputField.sendKeys("Herpson");
        inputField = driver.findElement(By.name("username"));
        inputField.sendKeys("Bob");
        inputField = driver.findElement(By.name("password"));
        inputField.sendKeys("admin");
        inputField.submit();
//        driver.findElement(By.xpath("//a[@href='/login']")).click();

        // input username & password
        Thread.sleep(500);
        inputField = driver.findElement(By.id("inputUsername"));
        inputField.sendKeys("Bob");
        inputField = driver.findElement(By.id("inputPassword"));
        inputField.sendKeys("admin");
        inputField.submit();

//        // input message
//        Thread.sleep(1000);
//        inputField = driver.findElement(By.id("messageText"));
//        inputField.sendKeys("Hi Grasshopper!");
//        inputField.submit();
//
//        Thread.sleep(1000);

        // retrieve message and print to console


//        for(int i = 0; i < 5; i++) {
//            inputField = driver.findElement(By.id("adjective"));
//            inputField.submit();
//        }
        //System.out.println(driver.findElement(By.xpath("//div")).getText());
        //List<WebElement> results = driver.findElements(By.className("conclusionMessage"));
//        List<WebElement> results = (driver.findElements(By.xpath("//div")));
//        for (WebElement element : results) {
//            System.out.println(driver.findElement(By.xpath("//div")).getText());
//        }
//            Thread.sleep(5000);
            //driver.quit();
        }
    }
//}
