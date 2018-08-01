package stackoversearch.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stackoversearch.Configuration;
import stackoversearch.pagesHelpers.PageAnnotationHelper;
import stackoversearch.pagesHelpers.PageHolder;

import static stackoversearch.WebDriverAffairs.WebDriverRunner.getAndCheckWebDriver;

public abstract class AbstractPage {

    AbstractPage() throws Exception {
        cPageAnnotationHelper = new PageAnnotationHelper(this);
        PageFactory.initElements(getAndCheckWebDriver(), this);
        PageHolder.putPageToCache(this);
    }

    public String getTitle() {
        return cPageAnnotationHelper.getPageTitle();
    }

    public WebElement getElement(String annotationName) throws Exception {
        return cPageAnnotationHelper.getElementByAnnotation(annotationName);
    }

    public boolean isApeared(String pageName) throws Exception {
        WebDriver driver = getAndCheckWebDriver();
        try {
            return new WebDriverWait(driver, Configuration.openTimeoutSeconds)
                    .until(ExpectedConditions.titleIs(PageHolder.getAbstractPageByTitle(pageName).getTitle()));
        } catch (TimeoutException e) {
            Log.info("The page " + pageName + " has not opened");
        }
        return false;
    }

    public boolean isAppearedDynamicPage(String pageName, String theme) throws Exception {
        WebDriver driver = getAndCheckWebDriver();
        try {
            return new WebDriverWait(driver, Configuration.openTimeoutSeconds)
                    .until(ExpectedConditions.titleContains(theme + PageHolder.getAbstractPageByTitle(pageName).getTitle()));
        } catch (TimeoutException e) {
            Log.info("Динамическая страница " + pageName + " с тайтлом " + theme + PageHolder.getAbstractPageByTitle(pageName).getTitle() + " не открылась");
        }
        return false;
    }

    public boolean isDisplayed(String elementName) throws Exception {
        WebDriver driver = getAndCheckWebDriver();
        try {
            new WebDriverWait(driver, Configuration.openTimeoutSeconds)
                    .until(ExpectedConditions.elementToBeClickable(this.getElement(elementName)));
        } catch (TimeoutException e) {
            Log.info("The element " + elementName + " is not found on the page with title " + driver.getTitle());
            return false;
        }
        return true;
    }

    private Logger Log = LogManager.getLogger(AbstractPage.class.getName());

    private PageAnnotationHelper cPageAnnotationHelper;
}
