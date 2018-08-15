//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.thunlp.misc;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Counter<KeyType> implements Iterable<Entry<KeyType, Long>> {
    protected Map<KeyType, Long> hash = new Hashtable();
    protected long total = 0L;

    public Iterator<Entry<KeyType, Long>> iterator() {
        return this.hash.entrySet().iterator();
    }

    public void clear() {
        this.hash.clear();
        this.total = 0L;
    }

    public int size() {
        return this.hash.size();
    }

    public long total() {
        return this.total;
    }

    public Counter() {
    }

    public void inc(Counter<KeyType> another) {
        Iterator i$ = another.hash.entrySet().iterator();

        while(i$.hasNext()) {
            Entry<KeyType, Long> e = (Entry)i$.next();
            this.inc(e.getKey(), (Long)e.getValue());
        }

    }

    public void inc(Collection<KeyType> container, long delta) {
        Iterator i$ = container.iterator();

        while(i$.hasNext()) {
            KeyType key = (KeyType) i$.next();
            this.inc(key, delta);
        }

    }

    public void inc(KeyType[] container, long delta) {
        Object[] arr$ = container;
        int len$ = container.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            KeyType key = (KeyType) arr$[i$];
            this.inc(key, delta);
        }

    }

    public void inc(KeyType key, long delta) {
        Long current = (Long)this.hash.get(key);
        if (current == null) {
            current = 0L;
        }

        if (current + delta == 0L) {
            this.hash.remove(key);
        } else {
            this.hash.put(key, current + delta);
        }

        this.total += delta;
    }

    public long get(KeyType key) {
        Long current = (Long)this.hash.get(key);
        if (current == null) {
            current = 0L;
        }

        return current;
    }
}
