package com.kakao.todolist.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {
    private WebDriver driver;
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/view/1");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test_elements() {
        List<WebElement> rows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        assertThat(rows.size(), is(10));

        List<WebElement> pageItems = driver.findElements(By.className("page-item"));
        assertThat(pageItems.size(), is(4));
        assertThat(pageItems.get(0).getText(), is("«\nPrevious"));
        assertThat(pageItems.get(1).getText(), is("1"));
        assertThat(pageItems.get(2).getText(), is("2"));
        assertThat(pageItems.get(3).getText(), is("»\nNext"));
    }

    @Test
    public void test_move_link() {
        List<WebElement> rows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        rows.get(3).findElement(By.tagName("a")).click();
        assertThat(driver.getCurrentUrl(), containsString("todo/4"));
    }

    @Test
    public void test_paging() {
        List<WebElement> pageItems = driver.findElements(By.className("page-item"));
        pageItems.get(2).click();
        assertThat(driver.getCurrentUrl(), containsString("view/2"));

        List<WebElement> secondPageRows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        assertThat(secondPageRows.size(), not(10));
    }
}
