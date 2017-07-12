package ru.otus.bvd;

import ru.otus.bvd.tester.Tester;

/**
 * Created by vadim on 09.07.17.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        System.out.println("----------Test on classes---------------");
        Tester.testrun(new Class<?>[]{Class.forName("ru.otus.bvd.example.ArrayListTest"), Class.forName("ru.otus.bvd.example.HashMapTest")});


        System.out.println("----------Test on package---------------");
        Tester.testrun("ru.otus.bvd.example");
    }
}
