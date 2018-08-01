package stackoversearch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import javax.annotation.Nullable;

/**
 *  Костыль для Firefox'a, по какой-топричине он непозваляет изменить опцию перехода по ссылкам (он открывает ссылки в новом окне вместо новой вкладки).
 *  Данный параметр лежит в ImmutableMap.
 *  Делает он это долго, поэтому чтобы корректно переключиться на новое окно, оно должно полностью загрузиться.
 *  Для этого сделал свой ExpectedCondition
 */

public class MyCondition implements ExpectedCondition {

    private int windowsCount;
    private WebDriver driver;

    public MyCondition(int windowsCount, WebDriver driver) {
        this.windowsCount = windowsCount;
        this.driver = driver;
    }

    @Nullable
    @Override
    public Object apply(@Nullable Object o) {
        return driver.getWindowHandles().size() == windowsCount;
    }
}
