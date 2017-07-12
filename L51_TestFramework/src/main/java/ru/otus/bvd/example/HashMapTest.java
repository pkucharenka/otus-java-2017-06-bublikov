package ru.otus.bvd.example;

import ru.otus.bvd.tester.Assert;
import ru.otus.bvd.tester.annotation.After;
import ru.otus.bvd.tester.annotation.Before;
import ru.otus.bvd.tester.annotation.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vadim on 11.07.17.
 */
public class HashMapTest {
    Map<String, String> map;

    @Before
    public void testprepare() {
        System.out.println("HashMapTest call before");
        map = new HashMap<>();
        map.put("KEY", "VALUE");
    }
    @Test
    public void testGet() {
        System.out.println("HashMapTest call test add");
        Assert.assertEquals(map.get("KEY"), "VALUE" );
    }
    @After
    public void testend() {
        System.out.println("HashMapTest call after");
        map.clear();
    }

}
