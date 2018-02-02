package pl.kubiczak.selenium.grid.firefox.without.marionette;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
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
    webDriver.get("https://saucelabs.com/test/guinea-pig");
    String title = webDriver.getTitle();

    assertThat(title, equalTo("I am a page title - Sauce Labs"));
  }

  @After
  public void quitWebDriverAfterUse() {
    if (webDriver != null) {
      webDriver.quit();
    }
  }
}
