package ru.otus.bvd.dataset;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("./");
        System.out.println(file.getAbsolutePath());
    }
}
