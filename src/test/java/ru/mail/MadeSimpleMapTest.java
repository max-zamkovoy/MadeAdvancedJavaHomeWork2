package ru.mail;

import org.junit.Test;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

public class MadeSimpleMapTest {
    
    private static final String KEY = "Key";
    private static final String KEY_SECOND = "Key second";
    private static final String VALUE = "Value";
    private static final String VALUE_SECOND = "Value second";
    
    @Test
    public void putOnKeyExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        assertEquals(VALUE, map.put(KEY, VALUE_SECOND));
    }

    @Test
    public void putOnKeyNotExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        assertNull(map.put(KEY_SECOND, VALUE_SECOND));
    }

    @Test
    public void putOnTwoEqualValues() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        map.put(KEY_SECOND, VALUE_SECOND);
        assertEquals(2, map.size());
    }

    @Test
    public void putOnCollision() {
        SimpleMap<String, String> map = new MadeSimpleMap<>(1);
        map.put(KEY, VALUE);
        map.put(KEY_SECOND, VALUE_SECOND);
        assertEquals(VALUE, map.get(KEY));
        assertEquals(VALUE_SECOND, map.get(KEY_SECOND));
    }

    @Test
    public void getOnKeyExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        assertEquals(VALUE, map.get(KEY));
    }

    @Test
    public void getOnKeyNotExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        assertNull(map.get(KEY));
    }

    @Test
    public void removeOnKeyExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        assertEquals(VALUE, map.remove(KEY));
        assertNull(map.get(KEY));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeOnKeyNotExist() {
        new MadeSimpleMap<>().remove(KEY);
    }

    @Test
    public void containsOnKeyExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        assertTrue(map.contains(KEY));
    }

    @Test
    public void containsOnKeyNotExist() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        assertFalse(map.contains(KEY_SECOND));
    }

    @Test
    public void sizeOnTwoEqualValues() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        map.put(KEY, VALUE_SECOND);
        map.put(KEY_SECOND, VALUE_SECOND);
        assertEquals(2, map.size());
    }

    @Test
    public void keySet() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        map.put(KEY, VALUE_SECOND);
        map.put(KEY_SECOND, VALUE_SECOND);

        Set<String> s = map.keySet();
        assertEquals(2, s.size());
        assertTrue(s.contains(KEY));
        assertTrue(s.contains(KEY_SECOND));
    }

    @Test
    public void values() {
        SimpleMap<String, String> map = new MadeSimpleMap<>();
        map.put(KEY, VALUE);
        map.put(KEY_SECOND, VALUE_SECOND);

        Collection<String> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(VALUE));
        assertTrue(values.contains(VALUE_SECOND));
    }
}
