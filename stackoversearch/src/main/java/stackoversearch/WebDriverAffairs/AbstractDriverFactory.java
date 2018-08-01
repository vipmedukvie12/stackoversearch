package stackoversearch.WebDriverAffairs;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import stackoversearch.Configuration;


import java.util.logging.Logger;

import static org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS;

abstract class AbstractDriverFactory {

  private static final Logger log = Logger.getLogger(AbstractDriverFactory.class.getName());
  abstract boolean supports();

  abstract WebDriver create();

  AbstractDriverFactory() {
  }

  DesiredCapabilities createCommonCapabilities() {
    DesiredCapabilities browserCapabilities = new DesiredCapabilities();
    browserCapabilities.setCapability(ACCEPT_SSL_CERTS, true);

    transferCapabilitiesFromSystemProperties(browserCapabilities);
    browserCapabilities = mergeCapabilitiesFromConfiguration(browserCapabilities);
    return browserCapabilities;
  }

  DesiredCapabilities mergeCapabilitiesFromConfiguration(DesiredCapabilities currentCapabilities) {
    return currentCapabilities.merge(Configuration.browserCapabilities);
  }

  private void transferCapabilitiesFromSystemProperties(DesiredCapabilities currentBrowserCapabilities) {
    String prefix = "capabilities.";
    for (String key : System.getProperties().stringPropertyNames()) {
      if (key.startsWith(prefix)) {
        String capability = key.substring(prefix.length());
        String value = System.getProperties().getProperty(key);
        log.config("Use " + key + "=" + value);
        if (value.equals("true") || value.equals("false")) {
          currentBrowserCapabilities.setCapability(capability, Boolean.valueOf(value));
        } else if (value.matches("^-?\\d+$")) { //if integer
          currentBrowserCapabilities.setCapability(capability, Integer.parseInt(value));
        } else {
          currentBrowserCapabilities.setCapability(capability, value);
        }
      }
    }
  }
}
