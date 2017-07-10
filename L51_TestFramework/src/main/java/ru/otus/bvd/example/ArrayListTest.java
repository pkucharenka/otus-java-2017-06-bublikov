package ru.otus.bvd.example;

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
        arr = new ArrayList<>();
        arr.add(0);
        System.out.println("call before");
    }
    @Test
    public void testrunAdd() {
        arr.add(1);
        System.out.println("call test add");
    }
    @Test
    public void testrunRemove() {
        arr.add(1);
        System.out.println("call test remove");
    }
    @After
    public void testend() {
        arr.clear();
        System.out.println("call after");
    }
}
