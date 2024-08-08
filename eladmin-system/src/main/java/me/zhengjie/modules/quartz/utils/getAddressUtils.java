package me.zhengjie.modules.quartz.utils;


import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * @author Zuohaitao
 * @date 2024-08-08 下午1:32
 * @describe 根据GPS获取省市区的地理信息
 */
public class getAddressUtils {

    /**
     * 百度地图 Api调用相关的百度AK
     */
    public final static String BAIDU_MAP_AK = "PWf2SsU8in7KIqcM9Vr4ChhBnPM8554u";

    /**
     * 根据经纬度调用百度API获取 地理位置信息，根据经纬度
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static JSONObject getAddressInfoByLngAndLat(String longitude,String latitude){
        JSONObject obj = new JSONObject();
        String location=latitude+","+longitude;
        //百度url  coordtype :bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
        String url ="http://api.map.baidu.com/reverse_geocoding/v3/?ak="+BAIDU_MAP_AK+"&output=json&coordtype=wgs84ll&location="+location;
        try {
            String json = loadJSON(url);
            obj = JSONObject.parseObject(json);
            System.out.println(obj.toString());
            // status:0 成功
            String success="0";
            String status = String.valueOf(obj.get("status"));
            if(success.equals(status)){
                String result = String.valueOf(obj.get("result"));
                JSONObject resultObj = JSONObject.parseObject(result);
                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                //JSON字符串转换成Java对象
                // AddressComponent addressComponentInfo = JSONObject.parseObject(addressComponent, AddressComponent.class);
                System.out.println("addressComponentInfo:"+addressComponent);
            }
        } catch (Exception e) {
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }
        return obj;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }

    /**
     * 根据GPS获取地址信息的HashMap
     * @param lon 经度
     * @param lat 纬度
     * @return 包含省、市、区信息的HashMap
     */
    public static HashMap<String, String> getAddressByGPS(String lon, String lat) {
        HashMap<String, String> addressMap = new HashMap<>();
        JSONObject obj = getAddressInfoByLngAndLat(lon, lat);
        if (obj != null && "0".equals(obj.getString("status"))) {
            JSONObject resultObj = obj.getJSONObject("result");
            JSONObject addressComponent = resultObj.getJSONObject("addressComponent");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            String district = addressComponent.getString("district");
            String street = addressComponent.getString("street");

            addressMap.put("省份", province);
            addressMap.put("城市", city);
            addressMap.put("区县", district);
            addressMap.put("道路", street);
        }
        return addressMap;
    }

    public static HashMap<String, String> getStartEndAddressByGPS(String RoadStartLon, String RoadStartLat, String RoadEndLon, String RoadEndLat) {
        HashMap<String, String> addressMap = new HashMap<>();
        JSONObject startObj = getAddressInfoByLngAndLat(RoadStartLon, RoadStartLat);
        if (startObj != null && "0".equals(startObj.getString("status"))) {
            JSONObject resultObj = startObj.getJSONObject("result");
            JSONObject addressComponent = resultObj.getJSONObject("addressComponent");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            String district = addressComponent.getString("district");
            String street = addressComponent.getString("street");

            addressMap.put("省份", province);
            addressMap.put("城市", city);
            addressMap.put("区县", district);
            addressMap.put("起点道路", street);
        }

        JSONObject endObj = getAddressInfoByLngAndLat(RoadEndLon, RoadEndLat);
        if (endObj != null && "0".equals(endObj.getString("status"))) {
            JSONObject resultObj = endObj.getJSONObject("result");
            JSONObject addressComponent = resultObj.getJSONObject("addressComponent");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            String district = addressComponent.getString("district");
            String street = addressComponent.getString("street");

            addressMap.put("省份", province);
            addressMap.put("城市", city);
            addressMap.put("区县", district);
            addressMap.put("终点道路", street);
        }


        return addressMap;
    }

    public static void main(String[] args) {
        getAddressInfoByLngAndLat("121.787199097","30.88788697383333");
    }

}
