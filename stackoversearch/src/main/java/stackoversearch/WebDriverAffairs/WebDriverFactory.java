package stackoversearch.WebDriverAffairs;


import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.internal.BuildInfo;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;
import static stackoversearch.Configuration.*;
import static stackoversearch.WebDriverAffairs.WebDriverRunner.isChrome;

public class WebDriverFactory {

  private static final Logger log = Logger.getLogger(WebDriverFactory.class.getName());

  private List<AbstractDriverFactory> factories = asList(
      new ChromeDriverFactory(),
      new FirefoxDriverFactory()
  );

  public WebDriver createWebDriver() {
    log.config("Configuration.browser=" + browser);
    log.config("Configuration.startMaximized=" + startMaximized);

    WebDriver webdriver = factories.stream()
        .filter(abstractDriverFactory -> abstractDriverFactory.supports())
        .findAny()
        .map(abstractDriverFactory -> abstractDriverFactory.create())
        .orElseGet(() -> new DefaultDriverFactory().create());

    webdriver = adjustBrowserSize(webdriver);

    logBrowserVersion(webdriver);
    logSeleniumInfo();
    return webdriver;
  }

  private WebDriver adjustBrowserSize(WebDriver driver) {
    if (startMaximized) {
      try {
        if (isChrome()) {
          maximizeChromeBrowser(driver.manage().window());
        } else {
          driver.manage().window().maximize();
        }
      } catch (Exception cannotMaximize) {
        cannotMaximize.printStackTrace();
      }
    }
    return driver;
  }


  private void maximizeChromeBrowser(WebDriver.Window window) {
    Dimension screenResolution = getScreenSize();
    window.setSize(screenResolution);
    window.setPosition(new Point(0, 0));
  }

  private Dimension getScreenSize() {
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    return new Dimension(
        (int) toolkit.getScreenSize().getWidth(),
        (int) toolkit.getScreenSize().getHeight());
  }

  private void logSeleniumInfo() {
      BuildInfo seleniumInfo = new BuildInfo();
      log.info("Selenium WebDriver v. " + seleniumInfo.getReleaseLabel() + " build time: " + seleniumInfo.getBuildTime());
  }

  private void logBrowserVersion(WebDriver webdriver) {
    if (webdriver instanceof HasCapabilities) {
      Capabilities capabilities = ((HasCapabilities) webdriver).getCapabilities();
      log.info("BrowserName=" + capabilities.getBrowserName() +
          " Version=" + capabilities.getVersion() + " Platform=" + capabilities.getPlatform());
    } else {
      log.info("BrowserName=" + webdriver.getClass().getName());
    }
  }
}
