package com.van.book3.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Van
 * @date 2020/3/18 - 10:51
 */
public class RandomUtil {
    public static Set getRandomSet(int max, int min, int size) {
        Random random = new Random();
        Set<Integer> set = new HashSet();
        while (set.size() <= size) {
            Integer number = random.nextInt(max) % (max - min + 1) + min;
            set.add(number);
        }
        return set;
    }

    public static int getRandomNum(int max, int min) {
        Random random = new Random();
        int number = random.nextInt(max) % (max - min + 1) + min;
        return number;
    }
}
