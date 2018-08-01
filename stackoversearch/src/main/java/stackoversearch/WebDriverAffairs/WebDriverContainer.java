package stackoversearch.WebDriverAffairs;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

public interface WebDriverContainer {
  void addListener(WebDriverEventListener listener);
  WebDriver setWebDriver(WebDriver webDriver);
  WebDriver getWebDriver();
  WebDriver getAndCheckWebDriver();
  void closeWebDriver();
  boolean hasWebDriverStarted();
  void clearBrowserCache();
  String getPageSource();
  String getCurrentUrl();
  String getCurrentFrameUrl();
}
