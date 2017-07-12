package ru.otus.bvd.tester;

/**
 * Created by vadim on 09.07.17.
 */
public class Assert {
    public static void assertEquals(Object sample, Object value ) {
        if (!sample.equals(value))
            throw new TestFailedException("Sample " + sample + " not equals value = " + value);
    }
}
