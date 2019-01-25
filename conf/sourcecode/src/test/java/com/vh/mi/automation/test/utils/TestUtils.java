package com.vh.mi.automation.test.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import com.vh.mi.automation.api.constants.SortOrder;
import org.testng.ITestNGMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by nimanandhar on 9/1/2014.
 */
public class TestUtils {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static <T>T randomItem(List<T> aList) {
        Preconditions.checkArgument(!aList.isEmpty());

        int randomIndex = RANDOM.nextInt(aList.size());
        return aList.get(randomIndex);
    }

    public static boolean containsAnnotation(ITestNGMethod method, Class annotationClass) {
        return method.getConstructorOrMethod().getMethod().getAnnotation(annotationClass) != null;
    }


    public static <T extends Comparable> List<T> sortList(List<T> aList) {
        List<T> tempList = new ArrayList<>(aList);
        Collections.sort(tempList);
        return tempList;
    }

    public static <T extends Comparable> List<T> sortList(List<T> data, SortOrder sortOrder) {
        List<T> tempList = new ArrayList<>(data);
        if (sortOrder == SortOrder.ASC)
            Collections.sort(tempList);
        else
            Collections.sort(tempList, Collections.reverseOrder());
        return tempList;
    }

    public static <T extends Comparable> boolean isSorted(List<T> aList) {
        List<T> sortedList = sortList(aList);
        return aList.equals(sortedList);
    }

    public static boolean isEqual(Table table1, Table table2) {
        if (table1.rowKeySet().size() != table2.rowKeySet().size()) {
            return false;
        }
        if (table1.columnKeySet().size() != table2.columnKeySet().size()) {
            return false;
        }

        for (Object row : table1.rowKeySet()) {
            for (Object column : table1.columnKeySet()) {
                if (!table1.get(row, column).equals(table2.get(row, column))) {
                    return false;
                }
            }
        }
        return true;
    }


}
