package stackoversearch.utils;

import java.util.HashMap;

public final class StorageUtils {

    private StorageUtils() {
    }

    public static void put(String key, Object value) {
        put(key, value, false);
    }

    private static void put(String key, Object value, boolean ignoreNull) {
        if (value == null && !ignoreNull) {
            throw new NullPointerException("Попытка положить в хранилище null!");
        }
        Storage.put(key.toUpperCase(), value);
    }

    public static void clean() {
        Storage.clean();
    }

    public static <T> T get(String  key) {
        return Storage.get(key.toUpperCase());
    }

    public static HashMap<Object, Object> hashMap() {
        return Storage.hashMap();
    }
}
