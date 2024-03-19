package me.zhengjie.modules.quartz.utils;

/**
 * @author Zuohaitao
 * @date 2024-01-25 9:14
 * @describe GPS转高德工具类
 */
/**
 * 经纬度 GPS转高德工具类
 */
public class GPSTransToAMapUtil {

    /**
     * 椭球参数
     */
    private static double pi = 3.14159265358979324;
    /**
     * 卫星椭球坐标投影到平面地图坐标系的投影因子
     */
    private static double a = 6378245.0;
    /**
     * 椭球的偏心率
     */
    private static double ee = 0.00669342162296594323;

    /**
     * 经纬度 GPS转高德
     *
     * @param wgLon GPS经度
     * @param wgLat GPS维度
     * @return 转化后的经纬度坐标
     */
    public static AMap transform(double wgLon, double wgLat) {
        if (outOfChina(wgLat, wgLon)) {
            return new AMap(wgLon, wgLat);
        }

        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double transLat = wgLat + dLat;
        double transLon = wgLon + dLon;
        return new AMap(transLon, transLat);
    }

    /**
     * 判断是否为国外坐标，，不在国内不做偏移
     *
     * @param lat
     * @param lon
     * @return
     */
    private static Boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    /**
     * 纬度转换
     *
     * @param x
     * @param y
     * @return
     */
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度转换
     *
     * @param x
     * @param y
     * @return
     */
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 高德经纬度类
     */
    public static class AMap {
        /**
         * 经度
         */
        private double longitude;

        /**
         * 维度
         */
        private double latitude;

        public AMap(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }
    }

    public static void main(String[] args) {
        double lon = 108.766167;
        double lat = 34.207948;
        AMap aMap = transform(lon, lat);
        // 108.766167,34.207948
        System.out.println("GPS转高德之前：" + lon + "," + lat);
        // 108.77088779593853,34.206482360676944
        System.out.println("GPS转高德之后：" + aMap.getLongitude() + "," + aMap.getLatitude());
        /**
         * 108.77090467665,34.206496310764
         * 高德API（https://lbs.amap.com/api/webservice/guide/api/convert）经纬度转换之后.
         * 两者误差不是很大
         */
    }

}

