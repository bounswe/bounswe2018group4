package com.memorist.memorist_android.helper;

import org.json.JSONArray;

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
}
