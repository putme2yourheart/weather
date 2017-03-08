package com.nocompany.frank.weather.utils;

/**
 * Created by Frank on 2017/2/10 0010.
 * 获取更新时间
 */

public class UpdateTime {

    /**
     * 对比更新时间，返回最后的更新时间
     *
     * @param t1 t1
     * @param t2 t2
     * @return 最后的更新时间
     */
    public static String cmp(String t1, String t2) {

        int y1 = Integer.parseInt(t1.substring(1, 5));
        int y2 = Integer.parseInt(t2.substring(1, 5));

        int m1 = Integer.parseInt(t1.substring(1, 5));
        int m2 = Integer.parseInt(t2.substring(1, 5));

        int d1 = Integer.parseInt(t1.substring(1, 5));
        int d2 = Integer.parseInt(t2.substring(1, 5));

        if (y1 > y2) {
            return t1;
        } else if (y1 < y2) {
            return t2;
        } else {
            if (m1 > m2) {
                return t1;
            } else if (m1 < m2) {
                return t2;
            } else {
                if (d1 > d2) {
                    return t1;
                } else if (d1 < d2) {
                    return t2;
                } else {
                    return t1;
                }
            }
        }
    }

    /**
     * 对比更新时间，返回最后的更新时间
     *
     * @param t1 t1
     * @param t2 t2
     * @param t3 t3
     * @return 最后的更新时间
     */
    public static String cmp(String t1, String t2, String t3) {
        return cmp(cmp(t1, t2), t3);
    }

    public static String getUpdateTime(String lastUpdate) {

        return lastUpdate.substring(0, lastUpdate.indexOf('T')).replace('-', ' ') + " " +
                lastUpdate.substring(lastUpdate.indexOf('T') + 1, lastUpdate.indexOf('+'));
    }

}
