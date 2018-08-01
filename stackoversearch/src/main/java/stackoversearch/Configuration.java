package stackoversearch;

import org.openqa.selenium.remote.DesiredCapabilities;
import static stackoversearch.config.Config.getProperty;

public class Configuration {
    /**
     *  все нижеописанные проперти конфигуриремы через maven через -D, наппример "-Dat.browser=chrome", если при запуске не указано, то берется из from env.properties
     */

    public static String browser  = System.getProperty("at.browser", getProperty("at.driver.type"));

    public static String baseUrl = System.getProperty("at.baseurl", getProperty("at.web.baseurl"));

    /**
     * Должен ли браузер возобновить работу после непредвиденного закрытия
     */
    public static boolean reopenBrowserOnFail = Boolean.parseBoolean(System.getProperty("at.reopenBrowserOnFail", "true"));

    /**
     * Таймаут в секундах для ожидания элемента.
     * по дефоолту: 15
     */
    public static long openTimeoutSeconds = Long.parseLong(System.getProperty( getProperty("at.openTimeoutSeconds"),  "15"));


    /**
     * Таймаут закрытия браузера
     * по дефолту: 5000 (милисекунд)
     */
    public static long closeBrowserTimeoutMs = Long.parseLong(System.getProperty("at.closeBrowserTimeout", "5000"));

    /**
     * Разворачивать ли на весь экран при стартебрауузера
     * по дефолту: true
     */
    public static boolean startMaximized = Boolean.parseBoolean(System.getProperty("at.start-maximized", "true"));

    /**
     * Капабилити браузера.
     * по дефолту: null
     */
    public static DesiredCapabilities browserCapabilities;

}

