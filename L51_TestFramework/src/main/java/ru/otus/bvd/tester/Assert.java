package ru.otus.bvd.tester;

/**
 * Created by vadim on 09.07.17.
 */
public class Assert {
    public static void assertEquals(int sample, int value ) {
        if (sample!=value)
            throw new TestFailedException("Sample " + sample + " not equals value = " + value);
    }
}
