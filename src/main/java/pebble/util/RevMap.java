package pebble.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RevMap implements Map {

    private final File file;
    private long lastModified;
    private Map inner = new HashMap<>();

    public RevMap(File reveFile) {
        this.file = reveFile;
        this.inner = RevManifestFileParser.parse(this.file);
        this.lastModified = this.file.lastModified();
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inner.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inner.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        checkAndRefresh();
        return inner.get(key);
    }

    private synchronized void checkAndRefresh() {
        if (this.file.lastModified() > this.lastModified) {
            this.lastModified = this.file.lastModified();
            this.inner = RevManifestFileParser.parse(this.file);
        }
    }

    @Override
    public Object put(Object key, Object value) {
        return inner.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return inner.remove(key);
    }

    @Override
    public void putAll(Map m) {
        inner.putAll(m);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public Set keySet() {
        return inner.keySet();
    }

    @Override
    public Collection values() {
        return inner.values();
    }

    @Override
    public Set<Entry> entrySet() {
        return inner.entrySet();
    }
}
