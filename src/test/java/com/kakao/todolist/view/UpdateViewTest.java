package com.kakao.todolist.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateViewTest {
    private WebDriver driver;
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/view/todo/9");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test2_whenClickList_thenChangeViewToList() {
        driver.findElement(By.id("goToList")).click();

        assertThat(driver.getCurrentUrl(), containsString("view/1"));
    }

}
