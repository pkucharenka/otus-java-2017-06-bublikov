package ru.otus.bvd.example;

import ru.otus.bvd.tester.Tester;

/**
 * Created by vadim on 09.07.17.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Tester.testrun(new Class<?>[]{Class.forName("ru.otus.bvd.example.ArrayListTest")});
    }
}
