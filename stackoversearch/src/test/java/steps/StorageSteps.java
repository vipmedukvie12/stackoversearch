package steps;

import cucumber.api.java.bg.И;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import stackoversearch.pagesHelpers.PageHolder;
import stackoversearch.utils.StorageUtils;

import java.util.ArrayList;
import java.util.List;

public class StorageSteps {
    public StorageSteps() {
    }

    @И("^в хранилище помещено значение \"([^\"]*)\" под именем \"([^\"]*)\"$")
    public void putValueIntoStorage(String value, String storageKey){
        StorageUtils.put(storageKey, value);
    }

    @И("^в хранилище под именем \"([^\"]*)\" сохранена тема результата поиска под номером \"(\\d+)\"$")
    public void isContainValue(String storageKey, int resultNumber) throws Exception {
        WebElement webElement = PageHolder.getAbstractPageByTitle("Главная страница").getElement("Таблица с результатами")
                .findElements(By.xpath("*")).get(resultNumber-1);
        String theme = webElement.findElement(By.xpath("td[2]")).getText();

        StorageUtils.put(storageKey, theme);
    }
}
