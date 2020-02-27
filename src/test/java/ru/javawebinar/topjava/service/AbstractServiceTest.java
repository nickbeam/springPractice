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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private static StringBuilder stringBuilder = new StringBuilder();
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String LINE = ANSI_GREEN + "+--------------+--------------------------------------------+-----------------+\n" + ANSI_RESET;
    private static final String TITLE = ANSI_GREEN + "|  Start time  |                   Test name                |  Duration (sec) |\n" + ANSI_RESET;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            String leftAlignFormat = "| %-12s | %-42s |";
            timeStart = System.currentTimeMillis();
            Calendar cal = Calendar.getInstance();
            stringBuilder
                    .append(ANSI_GREEN)
                    .append(String.format(leftAlignFormat, dateFormat.format(cal.getTime()), description.getMethodName()));
        }

        protected void finished(Description description) {
            String rightAlignFormat = " %-15s |%n";
            double timeEnd = System.currentTimeMillis();
            double seconds = (timeEnd - timeStart) / 1000.0;
            stringBuilder
                    .append(String.format(rightAlignFormat, new DecimalFormat("0.000").format(seconds)))
                    .append(ANSI_RESET);

            System.out.print(LINE);
            System.out.print(ANSI_GREEN + stringBuilder.substring(stringBuilder.length() - LINE.length() - 1, stringBuilder.length()) + ANSI_RESET);
            System.out.print(LINE);
        }
    };

    @BeforeClass
    public static void tableHead() {
        stringBuilder.append(LINE).append(TITLE).append(LINE);
    }

    @AfterClass
    public static void printTiming() {
        stringBuilder.append(LINE);
        System.out.print(stringBuilder);
    }
}
