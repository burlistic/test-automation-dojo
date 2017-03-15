import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Main {
    private static WebDriver driver = new FirefoxDriver();

    @BeforeClass
    public static void setUpClass() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }

    @After
    public void tearDown() {
        ((JavascriptExecutor)driver).executeScript("window.localStorage.clear();");
    }

    @Test
    public void shouldAddToDosAndRemove() throws Exception {
        driver.get("http://test-automation-dojo.com/todo");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement signIn = driver.findElement(By.id("sign-in"));

        username.sendKeys("toby");
        password.sendKeys("ninja");
        signIn.click();

        addToDo("buy milk");
        //addToDo("buy vegemite");
        //addToDo("make delicious milkshake!");

        // Remove
        WebElement destroy = driver.findElement(By.className("destroy"));
        destroy.click(); //...keysToSend: "" // uee when item hidden?

        //toDo.sendKeys(s);
        //toDo.sendKeys(Keys.RETURN);

        assertThat(currentCounter(), equalTo("0"));
    }


    @Test
    public void shouldAddToDosAndMarkAsDone() throws Exception {
        driver.get("http://test-automation-dojo.com/todo");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement signIn = driver.findElement(By.id("sign-in"));

        username.sendKeys("toby");
        password.sendKeys("ninja");
        signIn.click();

        addToDo("buy milk");
        //addToDo("buy vegemite");
        //addToDo("make delicious milkshake!");

        // Remove
        WebElement input = driver.findElement(By.className("toggle"));
        input.click();

        //toDo.sendKeys(s);
        //toDo.sendKeys(Keys.RETURN);

        assertThat(currentCounter(), equalTo("0"));
    }

    @Test
    public void failedLoginShouldDenyAccess() throws Exception {
        driver.get("http://test-automation-dojo.com/todo");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement signIn = driver.findElement(By.id("sign-in"));

        username.sendKeys("ran");
        password.sendKeys("bah");
        signIn.click();

        WebElement error = driver.findElement(By.id("error"));

        Thread.sleep(4000);

        assertThat(error.getText(), equalTo("Invalid credentials"));
    }


    private void addToDo(String s) {
        WebElement toDo = driver.findElement(By.className("new-todo"));
        toDo.sendKeys(s);
        toDo.sendKeys(Keys.RETURN);
    }


    private String currentCounter() {
        return driver.findElement(By.cssSelector(".todo-count > strong")).getText();
    }
}
