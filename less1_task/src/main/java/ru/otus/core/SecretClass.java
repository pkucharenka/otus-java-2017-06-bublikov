package ru.otus.core;

import java.util.Scanner;

/**
 * Created by vadim on 05.06.17.
 */
public class SecretClass {
    public static void main(String[] args) {
        System.out.print("Enter password:");
        Scanner scanner = new Scanner(System.in);
        String l = scanner.nextLine();
        System.out.println(l);
    }
}
