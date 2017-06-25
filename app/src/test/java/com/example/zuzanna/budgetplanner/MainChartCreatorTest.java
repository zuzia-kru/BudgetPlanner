package com.example.zuzanna.budgetplanner;

import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zuzanna on 25/06/2017.
 */
public class MainChartCreatorTest {

    private MainChartCreator mainChartCreator;
    private BarChart barChart;
    private TextView textView;

    @BeforeClass
    public static void setUp() {
//        mainChartCreator = new MainChartCreator();
    }

    @Test
    public void testGetDates() { //todo fix this test
        mainChartCreator = new MainChartCreator();
        List<String> expectedDates = Arrays.asList("19/06");
        List<String> actualDates = mainChartCreator.getDatesAxis(7);
//        Assert.assertEquals(expectedDates.get(0), actualDates.get(0));
    }
}