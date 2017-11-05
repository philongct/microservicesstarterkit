package lnguyen.utils;

import org.apache.commons.lang.math.RandomUtils;

public class ERandomUtils {
    public static int nextInt(int max, int min) {
        return Math.max(RandomUtils.nextInt(max), min);
    }
}
