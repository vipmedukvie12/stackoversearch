package stackoversearch.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import stackoversearch.annotations.Element;
import stackoversearch.annotations.Page;

@Page(title = " - Stack Overflow", name = "страница Stackoverflow")
public class StackoverflowPage extends AbstractPage {
    public StackoverflowPage() throws Exception {
        super();
    }

    @FindBy(xpath = "//h1/a")
    @Element(name = "Тема вопроса")
    public WebElement questionTheme;
}
