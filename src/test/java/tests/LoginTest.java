package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.DashboardPage;
import utils.ReportManager;

import java.time.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.DBConnection;

public class LoginTest extends BaseTest {
	
	//invalidlogin sc1
    @Test(priority = 0)
    public void invalidLogin() {
        LoginPage login = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        login.enterUsername(config.getProperty("username"));
        login.enterPassword("wrongpass");
        login.clickLogin();
        String error = login.getErrorMessage();
        Assert.assertTrue(error.contains("Epic sadface"), "Error message not displayed");
    }
    //validlogin sc2
    @Test(priority = 1)
    public void validLogin() {
    	ReportManager.createTest("Valid Login Test");
        LoginPage login = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        login.enterUsername(config.getProperty("username"));
        login.enterPassword(config.getProperty("password"));
        login.clickLogin();
        //assertions
        DashboardPage dash = new DashboardPage(driver);
        Assert.assertTrue(dash.isAtDashboard(), "Dashboard not visible after login");      
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
