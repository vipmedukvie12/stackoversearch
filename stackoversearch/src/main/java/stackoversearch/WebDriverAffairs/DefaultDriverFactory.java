package stackoversearch.WebDriverAffairs;

import org.openqa.selenium.WebDriver;

public class DefaultDriverFactory extends AbstractDriverFactory {

  public DefaultDriverFactory() {
  }

  @Override
  boolean supports() {
    return true;
  }

  @Override
  WebDriver create() {
    return new ChromeDriverFactory().create();
  }

}
