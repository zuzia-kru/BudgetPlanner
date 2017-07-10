package com.example.zuzanna.budgetplanner;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Zuzanna on 25/06/2017.
 */
public class MainChartCreatorTest {

    private MainChartCreator mainChartCreator;
    private BarChart barChart;
    private TextView textView;

    @Before
    public void setUp() {
        mainChartCreator = new MainChartCreator();
    }

    @Test
    public void testGetLastDaysFormatted_WithZeroDaysBack() {
        List<String> expectedDates = Arrays.asList("15/04");
        Calendar calendar = createCalendar(2020, 3, 15);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, 0);
        assertDates(expectedDates, actualDates);
    }

    @Test
    public void testGetLastDaysFormatted_WithNegativeDaysBack() {
        List<String> expectedDates = Arrays.asList("15/04");
        Calendar calendar = createCalendar(2020, 3, 15);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, -1);
        assertDates(expectedDates, actualDates);
    }

    @Test
    public void testGetLastDaysFormatted_WithSameMonth() {
        List<String> expectedDates = Arrays.asList("09/04", "10/04", "11/04", "12/04", "13/04", "14/04", "15/04");
        Calendar calendar = createCalendar(2020, 3, 15);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, 7);
        assertDates(expectedDates, actualDates);
    }

    @Test
    public void testGetLastDaysFormatted_WithDifferentMonth() {
        List<String> expectedDates = Arrays.asList("27/03", "28/03", "29/03", "30/03", "31/03", "01/04", "02/04");
        Calendar calendar = createCalendar(2020, 3, 2);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, 7);
        assertDates(expectedDates, actualDates);
    }

    @Test
    public void testGetLastDaysFormatted_WithDifferentMonthInFebruaryAndLeapYear() {
        List<String> expectedDates = Arrays.asList("25/02", "26/02", "27/02", "28/02", "29/02", "01/03", "02/03");
        Calendar calendar = createCalendar(2020, 2, 2);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, 7);
        assertDates(expectedDates, actualDates);
    }

    @Test
    public void testGetLastDaysFormatted_WithDifferentMonthInFebruaryAndNotLeapYear() {
        List<String> expectedDates = Arrays.asList("24/02", "25/02", "26/02", "27/02", "28/02", "01/03", "02/03");
        Calendar calendar = createCalendar(2021, 2, 2);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, 7);
        assertDates(expectedDates, actualDates);
    }

    @Test
    public void testGetLastDaysFormatted_WithDifferentYear() {
        List<String> expectedDates = Arrays.asList("27/12", "28/12", "29/12", "30/12", "31/12", "01/01", "02/01");
        Calendar calendar = createCalendar(2020, 0, 2);
        List<String> actualDates = mainChartCreator.getLastDaysFormatted(calendar, 7);
        assertDates(expectedDates, actualDates);
    }

    @NonNull
    private Calendar createCalendar(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        return calendar;
    }

    private void assertDates(List<String> expectedDates, List<String> actualDates) {
        for (int i = 0; i < expectedDates.size(); i++) {
            Assert.assertEquals(expectedDates.get(i), actualDates.get(i));
        }
    }
}