package steps;

import cucumber.api.java.Before;
import cucumber.api.java.bg.И;
import cucumber.api.java.en.Given;
import cucumber.api.java.ru.Пусть;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import stackoversearch.Configuration;
import stackoversearch.MyCondition;
import stackoversearch.pages.AbstractPage;
import stackoversearch.pagesHelpers.PageHolder;
import stackoversearch.utils.StorageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static stackoversearch.Configuration.baseUrl;
import static stackoversearch.WebDriverAffairs.WebDriverRunner.getAndCheckWebDriver;
import static stackoversearch.WebDriverAffairs.WebDriverRunner.isFirefox;

public class CommonSteps {
    public CommonSteps() {
    }

    @Before
    public void getDriver(){
        driver = getAndCheckWebDriver();
    }

    private AbstractPage openPageByUrlAndTitle(String url, String pageName) throws Exception {
        driver.navigate().to(url);
        Log.info("Переход на " + pageName + " с url " + url);
        return PageHolder.getAbstractPageByTitle(pageName);
    }

    private void typeTextInTextEditOnPageByTitle(String pageName, String elementName, String value) throws Exception {
        Log.info(String.format("Ввод значения %s в элемент %s на странице %s.", value, elementName, pageName));
        WebElement element = PageHolder.getAbstractPageByTitle(pageName).getElement(elementName);
        element.clear();
        element.sendKeys(value);
    }

    private void clickElementOnPageByTitle(String pageName, String elementName) throws Exception {
        Log.info(String.format("Клик по элементу %s на странице %s.", elementName, pageName));
        AbstractPage page = PageHolder.getAbstractPageByTitle(pageName);
        Assert.assertTrue(page.isDisplayed(elementName));
        page.getElement(elementName).click();
    }

    private void selectNewTab() {
        new WebDriverWait(driver, Configuration.openTimeoutSeconds).until(new MyCondition(2, driver));
        Set<String> handles = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(handles);
        String newTab = handlesList.get(handlesList.size() - 1);
        driver.switchTo().window(newTab);
        Log.info(String.format("Переключение на окно с url: %s.", driver.getCurrentUrl()));
    }

    @Пусть("^выполнен переход на \"Главная страница\"$")
    public void goToMainPage() throws Exception {
        openPageByUrlAndTitle(baseUrl,"Главная страница");

    }

    @И("^на странице \"([^\"]*)\" в выпадающем списке \"([^\"]*)\" выбрано значение \"([^\"]*)\"$")
    public void selectComboBoxElement(String pageName, String elementName, String value) throws Exception {
        WebElement element = PageHolder.getAbstractPageByTitle(pageName).getElement(elementName);
        new Select(element).selectByVisibleText(value);
    }

    @И("^на странице \"([^\"]*)\" в поле ввода \"([^\"]*)\" введено значение \"([^\"]*)\"$")
    public void typeTextInTextEditField(String pageName, String elementName, String value) throws Exception {
        typeTextInTextEditOnPageByTitle(pageName, elementName, value);
    }

    @И("^на странице \"([^\"]*)\" нажата кнопка \"([^\"]*)\"$")
    public void pushButton(String pageName, String elementName) throws Exception {
        clickElementOnPageByTitle(pageName, elementName);
    }

    @И("^проверено, что все результаты поиска содержат в теме \"([^\"]*)\", кол-во результатов на странице \"(\\d+)\"$")
    public void isContainValue(String value, int pageNumber) throws Exception {
        for (int i = 0; i < pageNumber; i++) {
            String theme = PageHolder.getAbstractPageByTitle("Главная страница").getElement("Таблица с результатами").findElements(By.xpath("*")).get(i)
                    .findElement(By.xpath("td[2]")).getText();
            Assert.assertTrue(theme.toLowerCase().contains(value.toLowerCase()), String.format("Тема результата под номером %s не содержит %s, тема: %s", i, value, theme));
        }
    }

    @И("^выполнен переход по ссылке в результате поиска под номером \"(\\d+)\"$")
    public void check(int resultNumber) throws Exception {
        PageHolder.getAbstractPageByTitle("Главная страница").getElement("Таблица с результатами").findElements(By.xpath("*")).get(resultNumber-1)
                .findElement(By.xpath("td[4]/a")).click();
        Log.info(String.format("Выполнен переход по ссылке в результате поиска под номером  %s.", resultNumber));
    }

    @И("^(?:открылась|отображена) страница \"([^\"]*)\"$")
    public void andPageOpened(String pageName) throws Exception {
        Assert.assertTrue(PageHolder.getAbstractPageByTitle(pageName).isApeared(pageName), "страница " + pageName + " не открылась");
    }

    @И("^(?:открылась|отображена) динамическая страница \"([^\"]*)\" с названием заголовка вопроса из хранилища \"([^\"]*)\"$")
    public void andDynamicPageOpened(String pageName, String storageKey) throws Exception {
        selectNewTab();
        Assert.assertTrue(PageHolder.getAbstractPageByTitle(pageName).isAppearedDynamicPage(pageName, StorageUtils.get(storageKey).toString())
                , "Динамическая страница " + pageName + " с заголовком " + StorageUtils.get(storageKey).toString()  + " не открылась");
    }

    @И("^проверено, что тема вопроса на StackOverFlow соответствует значению из хранилища \"([^\"]*)\"$")
    public void compareThemeWithStorageValue(String storageKey) throws Exception {
        WebElement element = PageHolder.getAbstractPageByTitle("страница Stackoverflow").getElement("Тема вопроса");
        new WebDriverWait(driver, Configuration.openTimeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
        String theme = element.getText();
        Assert.assertTrue(theme.equals(StorageUtils.get("Тема")), String.format("Тема вопроса на StackOverFlow не равна значению из хранилища, тема: %s, значение: %s"
                , theme, StorageUtils.get(storageKey)));
    }

    private static WebDriver driver;
    private Logger Log = LogManager.getLogger(CommonSteps.class.getName());
}
