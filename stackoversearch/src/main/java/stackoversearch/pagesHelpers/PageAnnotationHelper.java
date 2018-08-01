package stackoversearch.pagesHelpers;

import stackoversearch.annotations.Element;
import org.openqa.selenium.WebElement;
import stackoversearch.annotations.Page;
import stackoversearch.pages.AbstractPage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Класс для работы с аннотациями
 */

public class PageAnnotationHelper {

    public PageAnnotationHelper(AbstractPage newPageClass) throws Exception {
        this.pageClass = newPageClass;
        this.page = getPageAnnotation();
    }

    public String getPageTitle() { return page.title(); }

    /**
     *
     * @return - аннотацию Page из класса
     */

    private Page getPageAnnotation() throws Exception {
        if(pageClass.getClass().isAnnotationPresent(Page.class)){
            return (Page) pageClass.getClass().getAnnotation(Page.class);
        } else {
            throw new Exception("Annotation Page was not found in class " + pageClass);
        }
    }

    /**
     *
     * @return - WebElement с указанной аннотацией
     */

    public WebElement getElementByAnnotation(String annotationName) throws Exception {
        WebElement element = null;
        for (Field f : pageClass.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Element.class)&& f.getAnnotation(Element.class).name().equals(annotationName)) {
                f.setAccessible(true);
                element = (WebElement) f.get(pageClass);
            }
        }
        if(element == null) {
            throw new Exception(String.format("There are no elements with annotation '%s' on the page", annotationName));
        }
        return element;
    }


    private AbstractPage pageClass;
    private Page page;
}
