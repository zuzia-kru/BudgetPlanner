package com.example.zuzanna.budgetplanner;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Zuzanna on 25/06/2017.
 */
@RunWith(RobolectricTestRunner.class)
public class MainChartCreatorTest {

    private MainChartCreator mainChartCreator;
    @Mock
    private BarChart barChart;
    @Mock
    private TextView lastDaysView;
    @Mock
    private Calendar calendar;
    @Mock
    private DatabaseHandler databaseHandler;
    @Mock
    private Legend legend;
    @Mock
    private Description description;
    @Mock
    private XAxis xAxis;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainChartCreator = new MainChartCreator(barChart, lastDaysView, databaseHandler);
    }

    @Test
    public void testInvoke_WithNoLastWeekSpendings() {
        mainChartCreator.invoke();
        Assert.assertEquals(0, barChart.getVisibility());
        Assert.assertEquals(0, lastDaysView.getVisibility());
    }

    @Test
    public void testInvoke_WithGoodData() {
        Calendar testCalendar = createCalendar(2020, 7, 29);
        mainChartCreator.setCalendar(testCalendar);
        Mockito.mock(Color.class);
        Mockito.when(databaseHandler.getLastDays(1)).thenReturn(Collections.singletonMap("29/07", "1"));
        Mockito.when(barChart.getLegend()).thenReturn(legend);
        Mockito.when(barChart.getDescription()).thenReturn(description);
        Mockito.when(barChart.getXAxis()).thenReturn(xAxis);

        mainChartCreator.invoke();

        Assert.assertNotNull(mainChartCreator);
        Assert.assertNotNull(mainChartCreator.getBarChart()); //todo add more asserts here to make sure the right data is inserted
    }

    @Test
    public void testInvoke_WithBadData() {
        Mockito.mock(Color.class);
        Mockito.when(databaseHandler.getLastDays(1)).thenReturn(Collections.singletonMap("key", "1"));
        Mockito.when(barChart.getLegend()).thenReturn(legend);
        Mockito.when(barChart.getDescription()).thenReturn(description);
        Mockito.when(barChart.getXAxis()).thenReturn(xAxis);

        mainChartCreator.invoke();

        Assert.assertNotNull(mainChartCreator);
        Assert.assertNotNull(mainChartCreator.getBarChart()); //todo add more asserts here to make sure the right data is inserted
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
