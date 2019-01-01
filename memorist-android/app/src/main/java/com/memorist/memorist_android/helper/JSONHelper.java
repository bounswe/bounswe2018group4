package com.memorist.memorist_android.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHelper {

    public static JSONArray listToJSONArrayForString(ArrayList<String> list) {
        JSONArray arr = new JSONArray();

        for(String element : list) {
            arr.put(element);
        }

        return arr;
    }

    public static JSONArray listToJSONArrayForInteger(ArrayList<Integer> list) {
        JSONArray arr = new JSONArray();

        for(int element : list) {
            arr.put(element);
        }

        return arr;
    }

    public static JSONArray listToJSONArrayForLocation(String location1, String location2) {
        JSONArray arr = new JSONArray();
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();

        if(!location1.equals("")) {
            try {
                obj1.put("location_name", location1);
                obj1.put("location_coordinate_latitude", 37.23);
                obj1.put("location_coordinate_longitude", 29.12);
                arr.put(obj1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(!location2.equals("")) {
            try {
                obj2.put("location_name", location2);
                obj2.put("location_coordinate_latitude", 37.32);
                obj2.put("location_coordinate_longitude", 29.14);
                arr.put(obj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return arr;
    }
}
