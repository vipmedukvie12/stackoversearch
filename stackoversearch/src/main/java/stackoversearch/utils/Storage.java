package stackoversearch.utils;

import java.util.HashMap;

public final class Storage {
    private static ThreadStatic<HashMap<Object, Object>> storage = new ThreadStatic<>(new HashMap<>());

    private Storage() {}

    public static void put(Object key, Object value) {
        storage.get().put(key, value);
    }

    public static void clean() {
        storage.get().clear();
    }

    public static <T> T get(Object key) {
        return (T) storage.get().get(key);
    }

    public static HashMap<Object, Object> hashMap() {
        return storage.get();
    }
}
