import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BasePage extends BaseTest{

    Logger logger = LogManager.getLogger(BasePage.class);
    WebDriver driver;
    FluentWait<WebDriver> wait;
    JavascriptExecutor jsdriver;

    public void LogInsert(String text) {
        System.out.println(text);
        logger.info(text);
    }
    public BasePage(){
        driver = BaseTest.appiumDriver;
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(30)).
                pollingEvery(Duration.ofMillis(300)).
                ignoring(NoSuchElementException.class);
        jsdriver = (JavascriptExecutor) driver;
    }
    public WebElement findElement(By by){

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void sendKeys(By by, String text){

        findElement(by).sendKeys(text);
    }

    @Step("<seconds> saniye kadar bekle")
    public void waitForSeconds(long seconds){
        try{
            Thread.sleep(seconds*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Step("Scroll yap")
    public void scroll() throws InterruptedException {
        Dimension dimension = appiumDriver.manage().window().getSize();
        int start_x = (int) (dimension.width * 0.5);
        int start_y = (int) (dimension.height * 0.8);

        int end_x = (int) (dimension.width * 0.2);
        int end_y = (int) (dimension.height * 0.2);

        TouchAction touch = new TouchAction(appiumDriver);
        touch.press(PointOption.point(start_x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(end_x, end_y)).release().perform();
        TimeUnit.SECONDS.sleep(2);
    }


    @Step("id<id> li elemente tıkla")
    public void clickByid(String id) {
        if (id != null){
        appiumDriver.findElement(By.id(id)).click();
        System.out.println(id + "  elementine tikladi");
        LogInsert("Element bulundu");
        }else{
            LogInsert("Element bulunmadi");
        }
    }
    @Step("<xpath> li butona tıkla")
    public void clickByxpath(String xpath) throws InterruptedException {
        if (xpath != null){
            appiumDriver.findElement(By.xpath(xpath)).click();
            LogInsert(xpath+"Element bulundu");
            System.out.println(xpath + "  elementine tikladi");
        }else{
            LogInsert(xpath+"Element bulunmadi");
        }
    }

    @Step("<xpath> sayfasını kontrol et")
    public void controlApp(String xpath) throws InterruptedException {
        if (appiumDriver.findElement(By.xpath(xpath)).isDisplayed()){
            System.out.println("login sayfasi acildi");
            waitForSeconds(3);
        } else{
            System.out.println("login sayfasi acilmadi");
            waitForSeconds(3);
        }
    }

    @Step("Alisveris sayfasi acildi mi dogrula")
    public void controlAnasayfa() throws InterruptedException {
        if (appiumDriver.findElement(By.xpath("//*[@text='Anasayfa']")).isDisplayed()){
            System.out.println("Alisveris sayfasi acildi dogrulandı");
            waitForSeconds(3);
        } else{
            System.out.println("Alisveris sayfasi acilmadı");
        }
    }
    @Step("Kategoriler sayfasi acildi mi dogrula")
    public void controlKategoriler() throws InterruptedException {
        if (appiumDriver.findElement(By.xpath("//*[@content-desc=\"Kategoriler\"]/android.widget.TextView")).isDisplayed()){
            System.out.println("Kategoriler sayfasi dogrulandı");
            waitForSeconds(3);
        } else{
            System.out.println("Kategoriler sayfasi acilmadı");
        }
    }

    @Step("<xpath> Rastgele Ürün Seçildi")
    public void randomProduct(String xpath) {
        List<MobileElement> productList = appiumDriver.findElementsByXPath(xpath);
        int size = productList.size();
        Random rnd = new Random();
        int randomNum = rnd.nextInt(size);
        productList.get(randomNum).click();
    }
    @Step ("<id> li giriş bilgileri e posta adresini gir")
    public void Login (String id){
        if (id != null){
            sendKeys(By.id(id),"dikmanhilal@gmail.com");
            System.out.println(id + "  elementine tikladi");
            LogInsert(id+"E posta yazıldı");
        }else{
            LogInsert(id+"E posta yazılmadı");
        }
    }

    @Step ("<id> li giriş bilgileri şifre gir")
    public void Login2(String id2){
        if (id2 != null){
            sendKeys(By.id(id2),"1Q2w3e4r5t");
            System.out.println(id2 + "  elementine tikladi");
            LogInsert(id2+"sifre girildi");
        }else{
            LogInsert(id2+"sifre girilmedi");
        }
    }


}
