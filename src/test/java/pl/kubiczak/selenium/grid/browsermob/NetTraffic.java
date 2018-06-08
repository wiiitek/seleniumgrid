package pl.kubiczak.selenium.grid.browsermob;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.detro.browsermobproxyclient.BMPCProxy;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;

public class NetTraffic {

  private static final Logger LOG = LoggerFactory.getLogger(NetTraffic.class);

  private static final String HUB_URL = "http://localhost:4444/wd/hub";
  private static final String USE_DEFAULT_PAGE_REF = null;

  private BMPCProxy bmpcProxy;
  private RemoteWebDriver webDriver;

  @Before
  public void retrieveWebDriverWithProxy() throws MalformedURLException {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName("firefox");
    capabilities.setVersion("38");
    capabilities.setCapability("marionette", false);

    bmpcProxy = new ProxyClient().createProxyWithNewHar(USE_DEFAULT_PAGE_REF);
    Proxy seleniumProxy = ProxyClient.createSeleniumProxy(bmpcProxy);
    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

    webDriver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
  }

  @Test
  public void shouldCollectNetTraffic() {
    webDriver.get("https://www.whatismybrowser.com/");

    Har har = ProxyClient.readHar(bmpcProxy);
    List<HarEntry> entries = har.getLog().getEntries();

    for(HarEntry harEntry: entries) {
      LOG.info("{}", harEntry.getRequest().getUrl());
    }
    assertThat(entries, not(empty()));
  }

  @After
  public void quitWebDriverAndCloseProxy() {
    if (webDriver != null) {
      webDriver.quit();
    }
    if (bmpcProxy != null) {
      bmpcProxy.close();
    }
  }
}
