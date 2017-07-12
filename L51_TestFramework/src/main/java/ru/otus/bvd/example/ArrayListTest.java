package ru.otus.bvd.example;

import ru.otus.bvd.tester.Assert;
import ru.otus.bvd.tester.annotation.After;
import ru.otus.bvd.tester.annotation.Before;
import ru.otus.bvd.tester.annotation.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 09.07.17.
 */
public class ArrayListTest {
    List<Integer> arr;

    @Before
    public void testprepare() {
        System.out.println("ArrayListTest call before");
        arr = new ArrayList<>();
        arr.add(0);
    }
    @Test
    public void testAdd() {
        System.out.println("ArrayListTest call test add");
        Integer size1 = arr.size();
        arr.add(1);
        Assert.assertEquals(size1+1, arr.size());
    }
    @Test
    public void testBool() {
        System.out.println("ArrayListTest call test boolean");
        Assert.assertEquals(true, false);
    }
    @Test
    public void testRemove() {
        System.out.println("ArrayListTest call test remove");
        Integer size1 = arr.size();
        arr.remove(0);
        Assert.assertEquals(size1-1, arr.size());
    }
    @After
    public void testend() {
        System.out.println("ArrayListTest call after");
        arr.clear();
    }
}
