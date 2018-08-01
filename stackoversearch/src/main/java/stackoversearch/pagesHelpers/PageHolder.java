package stackoversearch.pagesHelpers;

import stackoversearch.annotations.Page;
import stackoversearch.pages.AbstractPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * класс для временного хранения Page
 */

public class PageHolder {

    private PageHolder(){

    }

    /**
     *  сохранить Page
     */

    public static void putPageToCache(AbstractPage abstractPage){
        if (!pageCache.containsKey(abstractPage.getTitle())) {
            pageCache.put(abstractPage.getTitle(), abstractPage);
        }
    }

    /**
     * Получить Page по имени
     * @param name - page name из аннотации
     */

    public static AbstractPage getAbstractPageByTitle(String name) throws Exception {
        if (pageCache.containsKey(name)){
            return pageCache.get(name);
        } else {
            return createAbstractPageByTitle(name);
        }
    }

    /**
     * Create page by annotated name
     * Создать page по именииз аннотации
     */

    private static AbstractPage createAbstractPageByTitle(String name) throws Exception {
        List<Class<?>> classList = ClassFinder.find(PAGE_PACKAGE);

        for (Class cClass : classList) {
            if(cClass.isAnnotationPresent(Page.class)) {
                Page page = (Page) cClass.getAnnotation(Page.class);
                if(page.name().equals(name)) {
                    AbstractPage abstractPage = (AbstractPage)  cClass.getConstructor().newInstance();
                    return abstractPage;
                }
            }
        }

        throw new Exception("Окно с заголовком " + name + " не может быть создано, т.к. класс с таким заголовком не найден.");
    }

    private static Map<String, AbstractPage> pageCache = new HashMap<>();
    private static final String PAGE_PACKAGE = "stackoversearch.pages";
}
