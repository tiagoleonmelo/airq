package deti.tqs.airq.functional;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import deti.tqs.airq.entities.AirQuality;


// Implementation of the Page Object pattern
public class AirQ {

    private WebDriver driver;
    JavascriptExecutor js;


    public AirQ() throws InterruptedException {

        this.driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        this.driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1366, 741));

    }


    public void search(String query)
    {
        
        driver.findElement(By.id("citySearch")).click();
        driver.findElement(By.id("citySearch")).sendKeys(query);

        driver.findElement(By.id("discoverButton")).click();

    }

    public boolean existResults() {
        List<WebElement> elements = driver.findElements(By.id("results"));
        return (elements.size() > 0);
    }

    public boolean foundDataFor(String city) {
        String text = driver.findElement(By.id("cityCountry")).getText();
        return (text.toLowerCase().contains(city.toLowerCase()));
    }

    public boolean dataDisplayed_isCorrect(AirQuality airQuality) {

        List<WebElement> attributes = driver.findElements(By.name("attributes"));
        String[] p;
        Map<String, String> map = airQuality.getAttributes();
        
        for (WebElement webElement : attributes) {

            p = webElement.getText().split(" - ");

            if(!map.containsKey(p[0]) || map.get(p[0]) != p[1]){
                return false;
            }
        }

        return true;

    }



    public void quit() {
        this.driver.quit();
    }

}