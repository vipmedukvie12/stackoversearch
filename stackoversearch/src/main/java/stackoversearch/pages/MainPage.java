package stackoversearch.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import stackoversearch.annotations.Element;
import stackoversearch.annotations.Page;

@Page(title = "StackOverSearch - новый запрос", name = "Главная страница")
public class MainPage extends AbstractPage{
    public MainPage() throws Exception {
        super();
    }

    @FindBy(id = "page_number")
    @Element(name = "Кол-во")
    public WebElement pageNumber;

    @FindBy(id = "intitle")
    @Element(name = "Поле ввода поиска")
    public WebElement textArea;

    @FindBy(xpath = "//input[@type='Submit']")
    @Element(name = "Кнопка Search")
    public WebElement searchButton;

    @FindBy(xpath = "//button[@class='btn btn-info btn-block btn-sm']")
    @Element(name = "Кнопка посмотреть все запросы")
    public WebElement showAllSearchesButton;

    @FindBy(xpath = "//table[@class='table table-hover table-striped']/tbody")
    @Element(name = "Таблица с результатами")
    public WebElement resultTable;

}
