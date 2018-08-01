package stackoversearch.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ThreadStatic<T> {
    private Class valueType = null;
    private HashMap<Long, T> storage = new HashMap<>();

    public ThreadStatic(T value) {
        this.set(value);
        this.valueType = value.getClass();
    }

    private ThreadStatic(){
    }

    public T get() {
        long id = Thread.currentThread().getId();

        try {
            if(!this.storage.containsKey(id)) {
                T newValue = (T) this.valueType.getConstructor().newInstance();
                this.storage.put(id, newValue);
            }
            return this.storage.get(id);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(T value) {
        long id = Thread.currentThread().getId();
        this.storage.put(id, value);
    }
}
