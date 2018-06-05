firefox-without-marionette
==========================

This project is to show how to use [Selenium Grid][selenium-grid] with old Firefox browser. It contains **minimal working configuration** that probably should be improved for production use.

From version 3.x Selenium uses [geckodriver] for interactions with Firefox browser and this driver supports Firefox version 55 and greater (see [Gecko Supported Firefoxen][supported-firefoxen]).

Sample Setup
------------
 
### Web Driver usage in JAVA code

Set `"marionette"` capability of required web driver to `false` (see [SeleniumGridTest](src/test/java/pl/kubiczak/selenium/grid/firefox/without/marionette/SeleniumGridTest.java#L27) class).

### Selenium Grid setup

1. Download [selenium standalone jar][selenium-standalone]
2. Start Selenium Grid hub:

        java -jar selenium-server-standalone-3.12.0.jar -role hub

3. Start Selenium Grid node:

        java -Dwebdriver.firefox.bin="D:/soft/Firefox38.6/firefox.exe" -jar selenium-server-standalone-3.12.0.jar -role node -hub "http://localhost:4444/grid/register/" -browser "browserName=firefox,version=38"

[selenium-grid]: https://github.com/SeleniumHQ/selenium/wiki/Grid2
[geckodriver]: https://github.com/mozilla/geckodriver
[supported-firefoxen]: https://github.com/mozilla/geckodriver#supported-firefoxen
[hub-and-node]: http://www.seleniumhq.org/docs/07_selenium_grid.jsp#selenium-grid-2-0
[selenium-standalone]: https://selenium-release.storage.googleapis.com/3.12/selenium-server-standalone-3.12.0.jar
