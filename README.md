firefox-without-marionette
==========================

This project is to show how to use [Selenium Grid][selenium-grid] with old Firefox browser. It contains **minimal working configuration** that probably should be improved for production use.

From version 3.x Selenium uses [geckodriver] for interactions with Firefox browser and this driver supports Firefox version 55 and greater (see [Gecko Supported Firefoxen][supported-firefoxen]).

So if we want to use older Firefox version, we need specific config for Selenium [hub and node][hub-and-node].

Sample Setup
------------
 
### Web Driver usage in JAVA code

Set `"marionette"` capability of required web driver to `false` (see [SeleniumGridTest](src/test/java/pl/kubiczak/selenium/grid/firefox/without/marionette/SeleniumGridTest.java#L25) class).

### Selenium Grid setup

1. Download [selenium standalone jar][selenium-standalone]
2. Start Selenium Grid hub:

        java -jar selenium-server-standalone-3.8.1.jar -role hub

3. Prepare JSON file with node config - `node-config.json`:

        {
          "capabilities": [
            {
              "acceptInsecureCerts": true,
              "browserName": "firefox",
              "marionette": false,
              "platform": "WIN8_1"
            }
          ],
          "hub": "http://localhost:4444"
        }

4. Start Selenium Grid node:

        java -Dwebdriver.firefox.marionette=false -Dwebdriver.firefox.bin="D:/soft/Firefox38.6/firefox.exe" -jar selenium-server-standalone-3.8.1.jar  -role node -nodeConfig node-config.json



[selenium-grid]: https://github.com/SeleniumHQ/selenium/wiki/Grid2
[geckodriver]: https://github.com/mozilla/geckodriver
[supported-firefoxen]: https://github.com/mozilla/geckodriver#supported-firefoxen
[hub-and-node]: http://www.seleniumhq.org/docs/07_selenium_grid.jsp#selenium-grid-2-0
[selenium-standalone]: http://selenium-release.storage.googleapis.com/3.8/selenium-server-standalone-3.8.1.jar
