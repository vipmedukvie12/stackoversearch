package stackoversearch.WebDriverAffairs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.logging.Logger;

class FirefoxDriverFactory extends AbstractDriverFactory {

  private static final Logger log = Logger.getLogger(FirefoxDriverFactory.class.getName());

  FirefoxDriverFactory() {
  }

  @Override
  boolean supports() {
    return WebDriverRunner.isFirefox();
  }

  @Override
  WebDriver create() {
    return createFirefoxDriver();
  }

  private WebDriver createFirefoxDriver() {
    FirefoxOptions options = createFirefoxOptions();
    return new FirefoxDriver(options);
  }

  FirefoxOptions createFirefoxOptions() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();

    firefoxOptions.addPreference("network.automatic-ntlm-auth.trusted-uris", "http://,https://");
    firefoxOptions.addPreference("network.automatic-ntlm-auth.allow-non-fqdn", true);
    firefoxOptions.addPreference("network.negotiate-auth.delegation-uris", "http://,https://");
    firefoxOptions.addPreference("network.negotiate-auth.trusted-uris", "http://,https://");
    firefoxOptions.addPreference("network.http.phishy-userpass-length", 255);
    firefoxOptions.addPreference("security.csp.enable", false);

    firefoxOptions.merge(createCommonCapabilities());
    firefoxOptions = transferFirefoxProfileFromSystemProperties(firefoxOptions);

    return firefoxOptions;
  }

  private FirefoxOptions transferFirefoxProfileFromSystemProperties(FirefoxOptions currentFirefoxOptions) {
    String prefix = "firefoxprofile.";
    FirefoxProfile profile = new FirefoxProfile();
    for (String key : System.getProperties().stringPropertyNames()) {
      if (key.startsWith(prefix)) {
        String capability = key.substring(prefix.length());
        String value = System.getProperties().getProperty(key);
        log.config("Use " + key + "=" + value);
        if (value.equals("true") || value.equals("false")) {
          profile.setPreference(capability, Boolean.valueOf(value));
        } else if (value.matches("^-?\\d+$")) { //if integer
          profile.setPreference(capability, Integer.parseInt(value));
        } else {
          profile.setPreference(capability, value);
        }
      }
    }
    return currentFirefoxOptions.setProfile(profile);
  }
}
