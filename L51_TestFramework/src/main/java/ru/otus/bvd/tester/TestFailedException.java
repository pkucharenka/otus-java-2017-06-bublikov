package ru.otus.bvd.tester;

/**
 * Created by vadim on 09.07.17.
 */
public class TestFailedException extends RuntimeException {
    public TestFailedException(String message) {
        super(message);
    }
}
