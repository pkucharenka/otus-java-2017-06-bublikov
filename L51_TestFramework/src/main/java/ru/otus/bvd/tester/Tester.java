package ru.otus.bvd.tester;


import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
            before = Class.forName("ru.otus.bvd.tester.annotation.Before");
            test = Class.forName("ru.otus.bvd.tester.annotation.Test");
            after = Class.forName("ru.otus.bvd.tester.annotation.After");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Map<Method, String> resultTest;
    static boolean testFailed;

    public static void testrun(Class[] classArr) {
        Method beforeMethod;
        List<Method> testMethods;
        Method afterMethod;

        for (Class<?> testClass : classArr) {

            beforeMethod = null;
            testMethods = new ArrayList<>();
            afterMethod = null;
            resultTest = new HashMap<>();
            testFailed = false;

            for (Method m : testClass.getMethods()) {
                if (m.getDeclaringClass() == testClass) {
                    if (m.getDeclaredAnnotations().length == 0)
                        continue;
                    Class<?> aClass = m.getDeclaredAnnotations()[0].annotationType();
                    if (aClass == before) {
                        beforeMethod = m;
                    } else if (aClass == test) {
                        testMethods.add(m);
                    } else if (aClass == after) {
                        afterMethod = m;
                    }
                }
            }

            Object testObject = ReflectionHelper.instantiate(testClass);

            if (beforeMethod != null) {
                try {
                    ReflectionHelper.callMethod(testObject, beforeMethod.getName());
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new TestFailedException(e.getMessage());
                }
            }

            for (Method testMethod : testMethods) {
                try {
                    ReflectionHelper.callMethod(testObject, testMethod.getName());
                    resultTest.put(testMethod, "OK");
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    if (e.getCause().getClass() == TestFailedException.class) {
                        resultTest.put(testMethod, e.getCause().getMessage());
                    } else {
                        resultTest.put(testMethod, e.getMessage());
                    }
                    testFailed = true;
                }
            }
            if (resultTest.isEmpty())
                continue;

            if (afterMethod != null) {
                try {
                    ReflectionHelper.callMethod(testObject, afterMethod.getName());
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new TestFailedException(e.getMessage());
                }
            }

            if (testFailed)
                System.out.print("Test " + testClass.getName() + " failed");
            else
                System.out.print("Test" + testClass.getName() + " successfuly");

            System.out.println(", result list:");
            for (Map.Entry<Method, String> entry : resultTest.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
            System.out.println();
        }
    }

    public static void testrun(String packageName) {
        Set<Class> allClasses = new HashSet<>();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(packageName))
                    allClasses.add(info.load());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        testrun( allClasses.toArray( new Class[allClasses.size()]) );
    }

}