package com.test;

import java.util.*;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/1, MarkHuang,new
 * </ul>
 * @since 2018/3/1
 */
public class TestDemo {
    public void eteTestDemo() {
        PostErrorMessage postErrorMessage = new PostErrorMessage();
        Map<Integer, Integer> dataMap = new LinkedHashMap<>();
        List<Map<Integer, Integer>> dataList = new ArrayList<>();
        dataMap.put(2, 0);
        dataMap.put(3, 3);
        dataList.add(dataMap);
        dataMap = new LinkedHashMap<>();
        dataMap.put(1, 1);
        dataMap.put(1, 1);
        dataList.add(dataMap);
        Iterator<Map<Integer, Integer>> iterator = dataList.iterator();
        int testFail = 0;
        while (iterator.hasNext()) {
            Assert anAssert = new Assert();
            int page = 1;
            for (int i = 1; i <= page; i++) {
                try {
                    Map<Integer, Integer> next = iterator.next();
                    for (int key : next.keySet()) {
                        anAssert.assertEquals(key + " must equal " + next.get(key), (long) key, (long) next.get(key));
                    }
                } catch (AssertErrorException e) {
                    testFail++;
                    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                        if (stackTraceElement.toString().contains("SelDemo")) {
                            postErrorMessage.postMsg("\nAt " + stackTraceElement.toString() + "\n" + e.getMessage());
                        }
                    }
                }
            }
        }
        System.out.println("測試成功率 = " + String.valueOf(((Assert.getTestCount() - testFail)
                / (double) Assert.getTestCount()) * 100) + " %");
    }
}
