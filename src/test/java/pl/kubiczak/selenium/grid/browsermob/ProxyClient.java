package pl.kubiczak.selenium.grid.browsermob;

import org.openqa.selenium.Proxy;

import com.github.detro.browsermobproxyclient.BMPCProxy;
import com.github.detro.browsermobproxyclient.manager.BMPCDefaultManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.lightbody.bmp.core.har.Har;

class ProxyClient {

  private static final String DEFAULT_API_HOST = "localhost";
  private static final int DEFAULT_API_PORT = 8080;

  private static final boolean CAPTURE_HEADERS = true;
  private static final boolean CAPTURE_CONTENT = true;

  private final BMPCDefaultManager manager;

  /**
   * Creates proxy manager for existing local Browsermob on port 8080.
   */
  ProxyClient() {
    manager = new BMPCDefaultManager(DEFAULT_API_HOST, DEFAULT_API_PORT);
  }

  /**
   * Creates new proxy on Browsermob.
   *
   * @param pageRef an ID for new Har collecting
   * @return a proxy that captures headers and content
   */
  BMPCProxy createProxyWithNewHar(String pageRef) {
    BMPCProxy proxy = manager.createProxy();
    proxy.newHar(pageRef, CAPTURE_HEADERS, CAPTURE_CONTENT, CAPTURE_CONTENT);
    return proxy;
  }

  /**
   * Creates and configures proxy for Selenium capabilities.
   *
   * @param bmpcProxy the proxy to use
   * @return selenium proxy that watches HTTP, SSL and socket traffic
   */
  static Proxy createSeleniumProxy(BMPCProxy bmpcProxy) {
    Proxy seleniumProxy = bmpcProxy.asSeleniumProxy();
    String proxyAddress = String.format("%s:%d", bmpcProxy.getAPIHost(), bmpcProxy.getProxyPort());
    seleniumProxy
        .setHttpProxy(proxyAddress)
        .setSslProxy(proxyAddress)
        .setSocksProxy(proxyAddress);
    return seleniumProxy;
  }

  /**
   * Reads traffic recorder so far.
   *
   * @param bmpcProxy the proxy to read the traffic from
   * @return HAR object with traffic data
   */
  static Har readHar(BMPCProxy bmpcProxy) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(bmpcProxy.har(), Har.class);
  }
}
