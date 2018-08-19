package com.kakao.todolist.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateViewTest {
    private WebDriver driver;
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/view/todo/create");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void add_linkedToDo_button() {
        List<WebElement> elements = driver.findElement(By.id("toDos")).findElements(By.tagName("option"));

        WebElement selected = null;
        for (WebElement element : elements) {
            if(element.isSelected()) {
                driver.findElement(By.id("addButton")).click();
                selected = element;
                break;
            }
        }

        assertThat(driver.findElement(By.id("addList")).getText(), is(selected.getText()));

    }

    @Test
    public void whenClickListButton_thenMoveListView() {
        driver.findElement(By.id("goToList")).click();
        assertThat(driver.getCurrentUrl(), containsString("view/1"));
    }

    @Test
    public void whenSaveWithoutWhatToDo_thenShowModalForInputWhatToDo() {
        driver.findElement(By.id("registerButton")).click();

        Wait wait = new FluentWait(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(5, SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement validateCreate = (WebElement) wait.until(driver -> ((WebDriver) driver).findElement(By.id("validateCreate")));
        assertTrue(validateCreate.isDisplayed());
    }
}
