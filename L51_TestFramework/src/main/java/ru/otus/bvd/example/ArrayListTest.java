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
    }
    @Test
    public void testrun() {
        arr.add(1);
    }
    @After
    public void testend() {
        arr.clear();
    }
}
