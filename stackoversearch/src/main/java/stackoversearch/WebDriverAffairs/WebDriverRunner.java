package stackoversearch.WebDriverAffairs;


import org.openqa.selenium.WebDriver;
import static stackoversearch.Configuration.browser;

public class WebDriverRunner {

  public static WebDriverContainer webdriverContainer = new WebDriverThreadLocalContainer();

  public static final String CHROME = "chrome";
  public static final String FIREFOX = "firefox";

  /**
   * Get the underlying instance of Selenium WebDriver, and assert that it's still alive.
   * @return new instance of WebDriver if the previous one has been closed meanwhile.
   */
  public static WebDriver getAndCheckWebDriver() {
    return webdriverContainer.getAndCheckWebDriver();
  }

  /**
   * Is Selenide configured to use Firefox browser
   */
  public static boolean isFirefox() {
    return FIREFOX.equalsIgnoreCase(browser);
  }

  /**
   * Is Selenide configured to use Chrome browser
   */
  public static boolean isChrome() {
    return CHROME.equalsIgnoreCase(browser);
  }

}
