package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class AbstractServiceTest {
    private double timeStart;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private static StringBuilder stringBuilder = new StringBuilder();

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            String leftAlignFormat = "| %-12s | %-42s |";
            timeStart = System.currentTimeMillis();
            Calendar cal = Calendar.getInstance();
            stringBuilder.append(String.format(leftAlignFormat, dateFormat.format(cal.getTime()), description.getMethodName()));
        }

        protected void finished(Description description) {
            String rightAlignFormat = " %-15s |%n";
            double timeEnd = System.currentTimeMillis();
            double seconds = (timeEnd - timeStart) / 1000.0;
            stringBuilder.append(String.format(rightAlignFormat, new DecimalFormat("0.000").format(seconds)));
            System.out.println("+--------------+--------------------------------------------+-----------------+");
            System.out.println(stringBuilder.substring(stringBuilder.length() - 81, stringBuilder.length() - 1));
            System.out.println("+--------------+--------------------------------------------+-----------------+");
        }
    };

    @BeforeClass
    public static void tableHead() {
        stringBuilder.append("+--------------+--------------------------------------------+-----------------+\n");
        stringBuilder.append("|  Start time  |                   Test name                |  Duration (sec) |\n");
        stringBuilder.append("+--------------+--------------------------------------------+-----------------+\n");
    }

    @AfterClass
    public static void printTiming() {
        stringBuilder.append("+--------------+--------------------------------------------+-----------------+\n");
        System.out.print(stringBuilder);
    }
}
