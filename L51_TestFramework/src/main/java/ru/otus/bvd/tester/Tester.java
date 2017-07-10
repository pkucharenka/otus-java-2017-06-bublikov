package ru.otus.bvd.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by vadim on 10.07.17.
 */
public class Tester {
    static Class<?> before = null;
    static Class<?> test = null;
    static Class<?> after = null;
    static {
        try {
            before  = Class.forName("ru.otus.bvd.tester.annotation.Before");
            test  = Class.forName("ru.otus.bvd.tester.annotation.Test");
            after  = Class.forName("ru.otus.bvd.tester.annotation.After");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    static Map<Method, String> resultTest = new HashMap<>();

    public static void testrun (Class[] classArr) {
        Method beforeMethod = null;
        List<Method> testMethods = new ArrayList<>();
        Method afterMethod = null;

        for (Class<?> testClass: classArr) {
            for (Method m : testClass.getMethods() ) {
                if (m.getDeclaringClass() == testClass) {
                    Class<?> aClass = m.getDeclaredAnnotations()[0].annotationType();
                    if (aClass == before) {
                        beforeMethod = m;
                    } else if (aClass == test) {
                        testMethods.add(m);
                    } else if ( aClass == after) {
                        afterMethod = m;
                    }
                }
            }

            Object testObject = ReflectionHelper.instantiate(testClass);

            ReflectionHelper.callMethod(testObject, beforeMethod.getName() );

            for (Method testMethod : testMethods) {

            }

            ReflectionHelper.callMethod(testObject, beforeMethod.getName() );

        }
    }
}
