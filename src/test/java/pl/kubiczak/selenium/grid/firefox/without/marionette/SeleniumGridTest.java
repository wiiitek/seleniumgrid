package pl.kubiczak.selenium.grid.firefox.without.marionette;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumGridTest {

  private static final String HUB_URL = "http://localhost:4444/wd/hub";

  private WebDriver webDriver;

  @Before
  public void retrieveWebDriver() throws MalformedURLException {
    FirefoxOptions opts = new FirefoxOptions();

    opts.setCapability("marionette", false);
    // or
    //opts.setLegacy(true);

    webDriver = new RemoteWebDriver(new URL(HUB_URL), opts);
  }

  @Test
  public void shouldRetrievePageTitle() {
    webDriver.get("https://www.whatismybrowser.com/");

    WebElement mainEl = webDriver.findElement(By.cssSelector("div.string-major"));
    String browserInfo = mainEl.getText();

    assertThat(browserInfo, startsWith("Firefox 38"));
  }

  @After
  public void quitWebDriverAfterUse() {
    if (webDriver != null) {
      webDriver.quit();
    }
  }
}
