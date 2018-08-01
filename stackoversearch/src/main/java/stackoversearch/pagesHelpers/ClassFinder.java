package stackoversearch.pagesHelpers;


import cucumber.runtime.io.ClasspathResourceLoader;
import cucumber.runtime.io.Resource;
import cucumber.runtime.io.ResourceLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ClassFinder {

    /**
     * Поиск классов в пэкэдже
     *
     * @return - лист классов в пэкедже
     */
    public static List<Class<?>> find(String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceLoader resourceLoader = new ClasspathResourceLoader(classLoader);

        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        Iterator resourceIterator = resourceLoader.resources(scannedPath, CLASS_FILE_SUFFIX).iterator();

        if(!resourceIterator.hasNext()) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR_TEMPLATE, scannedPath, scannedPackage));
        }

        while (resourceIterator.hasNext()) {
            Resource classResource = (Resource) resourceIterator.next();
            String className = classResource.getClassName(CLASS_FILE_SUFFIX);
            try {
                classes.add(classLoader.loadClass(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }

    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String BAD_PACKAGE_ERROR_TEMPLATE = "Не удалось получить ресурсы по поти '%s'. Вы уверены, что пэкэдж '%s' существует?";
}
